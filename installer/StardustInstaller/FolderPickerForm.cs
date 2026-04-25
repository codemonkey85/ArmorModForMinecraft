using System.Drawing;

namespace StardustInstaller;

public sealed class FolderPickerForm : Form
{
    private readonly Label _heading = new();
    private readonly ListBox _instances = new();
    private readonly Label _pathPreview = new();
    private readonly Button _browseButton = new();
    private readonly Button _okButton = new();
    private readonly Button _cancelButton = new();

    public string? SelectedFolder { get; private set; }

    public FolderPickerForm(string? currentFolder)
    {
        Text = "Choose mods folder";
        ClientSize = new Size(540, 360);
        StartPosition = FormStartPosition.CenterParent;
        FormBorderStyle = FormBorderStyle.FixedDialog;
        MaximizeBox = false;
        MinimizeBox = false;
        Font = new Font("Segoe UI", 9.5f);

        _heading.Text = "Detected CurseForge instances (double-click to select):";
        _heading.Location = new Point(15, 15);
        _heading.AutoSize = true;

        _instances.Location = new Point(15, 40);
        _instances.Size = new Size(510, 200);
        _instances.IntegralHeight = false;
        _instances.DisplayMember = nameof(CurseForgeInstance.Name);
        _instances.SelectedIndexChanged += OnSelectionChanged;
        _instances.DoubleClick += (_, _) => AcceptSelectedInstance();

        var detected = CurseForgeDetector.Find();
        foreach (var inst in detected)
        {
            int idx = _instances.Items.Add(inst);
            if (string.Equals(inst.ModsPath, currentFolder, StringComparison.OrdinalIgnoreCase))
            {
                _instances.SelectedIndex = idx;
            }
        }
        if (detected.Count == 0)
        {
            _instances.Items.Add("(no CurseForge instances detected)");
            _instances.Enabled = false;
        }

        _pathPreview.Location = new Point(15, 248);
        _pathPreview.Size = new Size(510, 20);
        _pathPreview.AutoEllipsis = true;
        _pathPreview.ForeColor = SystemColors.GrayText;
        _pathPreview.Text = string.Empty;

        _browseButton.Text = "Browse manually...";
        _browseButton.Location = new Point(15, 285);
        _browseButton.Size = new Size(170, 28);
        _browseButton.Click += OnBrowseClick;

        _okButton.Text = "OK";
        _okButton.Location = new Point(355, 285);
        _okButton.Size = new Size(80, 28);
        _okButton.Click += (_, _) => AcceptSelectedInstance();

        _cancelButton.Text = "Cancel";
        _cancelButton.Location = new Point(445, 285);
        _cancelButton.Size = new Size(80, 28);
        _cancelButton.DialogResult = DialogResult.Cancel;

        AcceptButton = _okButton;
        CancelButton = _cancelButton;

        Controls.AddRange(new Control[]
        {
            _heading, _instances, _pathPreview,
            _browseButton, _okButton, _cancelButton
        });
    }

    private void OnSelectionChanged(object? sender, EventArgs e)
    {
        _pathPreview.Text = _instances.SelectedItem is CurseForgeInstance inst ? inst.ModsPath : string.Empty;
    }

    private void OnBrowseClick(object? sender, EventArgs e)
    {
        using var dlg = new FolderBrowserDialog
        {
            Description = "Select your Minecraft mods folder",
            UseDescriptionForTitle = true,
            ShowNewFolderButton = true
        };
        if (dlg.ShowDialog(this) == DialogResult.OK)
        {
            SelectedFolder = dlg.SelectedPath;
            DialogResult = DialogResult.OK;
            Close();
        }
    }

    private void AcceptSelectedInstance()
    {
        if (_instances.SelectedItem is CurseForgeInstance inst)
        {
            SelectedFolder = inst.ModsPath;
            DialogResult = DialogResult.OK;
            Close();
        }
    }
}
