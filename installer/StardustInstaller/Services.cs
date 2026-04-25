using System.Net.Http;
using System.Net.Http.Headers;
using System.Net.Http.Json;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace StardustInstaller;

public sealed class Config
{
    public string? ModsFolder { get; set; }

    private static string ConfigPath => Path.Combine(
        Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData),
        "StardustInstaller", "config.json");

    public static Config Load()
    {
        try
        {
            var path = ConfigPath;
            if (File.Exists(path))
            {
                var json = File.ReadAllText(path);
                var cfg = JsonSerializer.Deserialize<Config>(json);
                if (cfg is not null) return cfg;
            }
        }
        catch
        {
            // Corrupted config -- start fresh.
        }
        return new Config();
    }

    public void Save()
    {
        var path = ConfigPath;
        Directory.CreateDirectory(Path.GetDirectoryName(path)!);
        File.WriteAllText(path, JsonSerializer.Serialize(this, new JsonSerializerOptions { WriteIndented = true }));
    }
}

public sealed record GitHubAsset(
    [property: JsonPropertyName("name")] string Name,
    [property: JsonPropertyName("browser_download_url")] string DownloadUrl,
    [property: JsonPropertyName("size")] long Size);

public sealed record GitHubRelease(
    [property: JsonPropertyName("tag_name")] string TagName,
    [property: JsonPropertyName("name")] string? Name,
    [property: JsonPropertyName("assets")] List<GitHubAsset> Assets);

public sealed class GitHubClient
{
    private static readonly HttpClient Http = CreateClient();
    private readonly string _owner;
    private readonly string _repo;

    public GitHubClient(string owner, string repo)
    {
        _owner = owner;
        _repo = repo;
    }

    private static HttpClient CreateClient()
    {
        var client = new HttpClient();
        client.DefaultRequestHeaders.UserAgent.Add(
            new ProductInfoHeaderValue("StardustInstaller", "1.0"));
        client.DefaultRequestHeaders.Accept.Add(
            new MediaTypeWithQualityHeaderValue("application/vnd.github+json"));
        return client;
    }

    public async Task<GitHubRelease> GetLatestReleaseAsync()
    {
        var url = $"https://api.github.com/repos/{_owner}/{_repo}/releases/latest";
        var release = await Http.GetFromJsonAsync<GitHubRelease>(url)
            ?? throw new InvalidOperationException("GitHub returned no release.");
        return release;
    }

    public async Task DownloadAsync(string url, string destination, IProgress<int> progress)
    {
        using var resp = await Http.GetAsync(url, HttpCompletionOption.ResponseHeadersRead);
        resp.EnsureSuccessStatusCode();
        var total = resp.Content.Headers.ContentLength ?? -1L;
        await using var src = await resp.Content.ReadAsStreamAsync();
        await using var dst = File.Create(destination);
        var buffer = new byte[81920];
        long read = 0;
        int n;
        while ((n = await src.ReadAsync(buffer)) > 0)
        {
            await dst.WriteAsync(buffer.AsMemory(0, n));
            read += n;
            if (total > 0)
            {
                progress.Report((int)(read * 100 / total));
            }
        }
        progress.Report(100);
    }
}

public sealed record CurseForgeInstance(string Name, string ModsPath);

public static class CurseForgeDetector
{
    public static IReadOnlyList<CurseForgeInstance> Find()
    {
        var results = new List<CurseForgeInstance>();
        string[] roots =
        {
            Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile),
                "curseforge", "minecraft", "Instances"),
            Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.MyDocuments),
                "curseforge", "minecraft", "Instances"),
        };
        foreach (var root in roots)
        {
            if (!Directory.Exists(root)) continue;
            foreach (var dir in Directory.EnumerateDirectories(root))
            {
                var modsPath = Path.Combine(dir, "mods");
                if (Directory.Exists(modsPath))
                {
                    results.Add(new CurseForgeInstance(Path.GetFileName(dir), modsPath));
                }
            }
        }
        return results;
    }
}
