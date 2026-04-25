#!/usr/bin/env bash
set -euo pipefail

usage() {
    cat <<'EOF'
Usage: scripts/release.sh <version> [--yes]

Bumps mod_version in gradle.properties, commits, tags vX.Y.Z, and pushes
both the commit and the tag to origin/main. The release.yml workflow
then builds the jar and publishes a GitHub Release.

Arguments:
    <version>   Semver-style version (e.g. 0.2.0)

Options:
    -y, --yes   Skip the interactive confirmation prompt
    -h, --help  Show this help and exit
EOF
}

VERSION=""
SKIP_CONFIRM=false

while [[ $# -gt 0 ]]; do
    case "$1" in
        -h|--help) usage; exit 0 ;;
        -y|--yes)  SKIP_CONFIRM=true; shift ;;
        -*)        echo "Unknown flag: $1" >&2; usage >&2; exit 1 ;;
        *)
            if [[ -z "$VERSION" ]]; then
                VERSION="$1"
            else
                echo "Unexpected argument: $1" >&2; usage >&2; exit 1
            fi
            shift
            ;;
    esac
done

if [[ -z "$VERSION" ]]; then
    echo "Error: version argument required" >&2
    usage >&2
    exit 1
fi

if [[ ! "$VERSION" =~ ^[0-9]+\.[0-9]+\.[0-9]+$ ]]; then
    echo "Error: version must be X.Y.Z (got: $VERSION)" >&2
    exit 1
fi

TAG="v$VERSION"

cd "$(dirname "$0")/.."

CURRENT_BRANCH="$(git rev-parse --abbrev-ref HEAD)"
if [[ "$CURRENT_BRANCH" != "main" ]]; then
    echo "Error: must be on the main branch (currently on $CURRENT_BRANCH)" >&2
    exit 1
fi

if ! git diff --quiet || ! git diff --cached --quiet; then
    echo "Error: working tree has uncommitted changes" >&2
    git status --short >&2
    exit 1
fi

if git rev-parse --verify --quiet "$TAG" >/dev/null; then
    echo "Error: tag $TAG already exists locally" >&2
    exit 1
fi

if git ls-remote --tags --exit-code origin "refs/tags/$TAG" >/dev/null 2>&1; then
    echo "Error: tag $TAG already exists on origin" >&2
    exit 1
fi

git fetch origin main --quiet
LOCAL_HEAD="$(git rev-parse HEAD)"
REMOTE_HEAD="$(git rev-parse origin/main)"
if [[ "$LOCAL_HEAD" != "$REMOTE_HEAD" ]]; then
    echo "Error: local main is not in sync with origin/main." >&2
    echo "  local:  $LOCAL_HEAD" >&2
    echo "  remote: $REMOTE_HEAD" >&2
    echo "Run 'git pull --ff-only' (or rebase) and try again." >&2
    exit 1
fi

CURRENT_VERSION="$(grep -E '^mod_version=' gradle.properties | cut -d= -f2)"

if [[ "$CURRENT_VERSION" == "$VERSION" ]]; then
    SKIP_BUMP=true
else
    SKIP_BUMP=false
fi

cat <<EOF
About to release:
  current mod_version: $CURRENT_VERSION
  new mod_version:     $VERSION
  tag:                 $TAG

The script will:
EOF
if [[ "$SKIP_BUMP" == "true" ]]; then
    cat <<EOF
  1. Skip the version bump (already at $VERSION)
  2. Create local tag $TAG on the current HEAD
  3. Push the tag (which triggers release.yml)
EOF
else
    cat <<EOF
  1. Bump mod_version in gradle.properties
  2. Commit "Release v$VERSION"
  3. Create local tag $TAG
  4. Push commit, then push tag (which triggers release.yml)
EOF
fi

if [[ "$SKIP_CONFIRM" != "true" ]]; then
    read -p "Proceed? [y/N] " -n 1 -r REPLY
    echo
    if [[ ! "$REPLY" =~ ^[Yy]$ ]]; then
        echo "Aborted."
        exit 1
    fi
fi

if [[ "$SKIP_BUMP" != "true" ]]; then
    sed -i.bak "s/^mod_version=.*/mod_version=$VERSION/" gradle.properties
    rm gradle.properties.bak
    git add gradle.properties
    git commit -m "Release v$VERSION"
fi

git tag "$TAG"

git push origin main
git push origin "$TAG"

echo
echo "Pushed v$VERSION. Watch the release build at:"
echo "  https://github.com/codemonkey85/ArmorModForMinecraft/actions"
