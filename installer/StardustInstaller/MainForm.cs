using System.Diagnostics;
using System.Drawing;

namespace StardustInstaller;

public sealed class MainForm : Form
{
    private const string RepoOwner = "codemonkey85";
    private const string RepoName = "ArmorModForMinecraft";
    private const string TargetJarPrefix = "stardust-";

    private readonly Label _folderHeading = new();
    private readonly Label _folderLabel = new();
    private readonly Button _changeFolderButton = new();
    private readonly Button _updateButton = new();
    private readonly Label _statusLabel = new();
    private readonly ProgressBar _progress = new();
    private readonly Config _config;

    public MainForm()
    {
        Text = "Stardust Mod Installer";
        ClientSize = new Size(520, 240);
        StartPosition = FormStartPosition.CenterScreen;
        FormBorderStyle = FormBorderStyle.FixedDialog;
        MaximizeBox = false;
        Font = new Font("Segoe UI", 9.5f);

        _config = Config.Load();

        _folderHeading.Text = "Mods folder:";
        _folderHeading.Location = new Point(20, 18);
        _folderHeading.AutoSize = true;

        _folderLabel.Location = new Point(20, 40);
        _folderLabel.Size = new Size(380, 24);
        _folderLabel.AutoEllipsis = true;
        _folderLabel.BorderStyle = BorderStyle.FixedSingle;
        _folderLabel.Padding = new Padding(4, 4, 4, 4);
        _folderLabel.TextAlign = ContentAlignment.MiddleLeft;

        _changeFolderButton.Text = "Change...";
        _changeFolderButton.Location = new Point(410, 40);
        _changeFolderButton.Size = new Size(90, 24);
        _changeFolderButton.Click += OnChangeFolderClick;

        _updateButton.Text = "Update Mod";
        _updateButton.Location = new Point(170, 90);
        _updateButton.Size = new Size(180, 50);
        _updateButton.Font = new Font("Segoe UI", 12f, FontStyle.Bold);
        _updateButton.Click += OnUpdateClick;

        _statusLabel.Location = new Point(20, 160);
        _statusLabel.Size = new Size(480, 22);
        _statusLabel.TextAlign = ContentAlignment.MiddleLeft;

        _progress.Location = new Point(20, 195);
        _progress.Size = new Size(480, 14);
        _progress.Style = ProgressBarStyle.Continuous;
        _progress.Visible = false;

        Controls.AddRange(new Control[]
        {
            _folderHeading, _folderLabel, _changeFolderButton,
            _updateButton, _statusLabel, _progress
        });

        RefreshFolderState();
    }

    private void RefreshFolderState()
    {
        bool hasFolder = !string.IsNullOrEmpty(_config.ModsFolder);
        _folderLabel.Text = hasFolder ? _config.ModsFolder! : "(not set)";
        _updateButton.Enabled = hasFolder;
        _statusLabel.Text = hasFolder
            ? "Ready. Click Update Mod to install the latest release."
            : "Click Change... to pick your mods folder.";
    }

    private void OnChangeFolderClick(object? sender, EventArgs e)
    {
        using var dlg = new FolderPickerForm(_config.ModsFolder);
        if (dlg.ShowDialog(this) == DialogResult.OK && !string.IsNullOrEmpty(dlg.SelectedFolder))
        {
            _config.ModsFolder = dlg.SelectedFolder;
            _config.Save();
            RefreshFolderState();
        }
    }

    private async void OnUpdateClick(object? sender, EventArgs e)
    {
        if (string.IsNullOrEmpty(_config.ModsFolder)) return;
        if (!Directory.Exists(_config.ModsFolder))
        {
            _statusLabel.Text = $"Mods folder doesn't exist: {_config.ModsFolder}";
            return;
        }

        _updateButton.Enabled = false;
        _changeFolderButton.Enabled = false;
        _progress.Visible = true;
        _progress.Value = 0;

        try
        {
            var client = new GitHubClient(RepoOwner, RepoName);
            _statusLabel.Text = "Checking for the latest release...";
            var release = await client.GetLatestReleaseAsync();
            var jar = release.Assets.FirstOrDefault(a => a.Name.EndsWith(".jar", StringComparison.OrdinalIgnoreCase));
            if (jar is null)
            {
                _statusLabel.Text = $"Release {release.TagName} has no .jar asset.";
                return;
            }

            _statusLabel.Text = $"Downloading {jar.Name}...";
            var tempPath = Path.Combine(Path.GetTempPath(), jar.Name);
            await client.DownloadAsync(jar.DownloadUrl, tempPath, new Progress<int>(p => _progress.Value = p));

            foreach (var existing in Directory.EnumerateFiles(_config.ModsFolder, $"{TargetJarPrefix}*.jar"))
            {
                File.Delete(existing);
            }

            var dest = Path.Combine(_config.ModsFolder, jar.Name);
            File.Move(tempPath, dest, overwrite: true);

            _statusLabel.Text = $"Done. Installed {release.TagName} ({jar.Name}).";
        }
        catch (Exception ex)
        {
            _statusLabel.Text = "Error: " + ex.Message;
            Debug.WriteLine(ex);
        }
        finally
        {
            _progress.Visible = false;
            _updateButton.Enabled = !string.IsNullOrEmpty(_config.ModsFolder);
            _changeFolderButton.Enabled = true;
        }
    }
}
