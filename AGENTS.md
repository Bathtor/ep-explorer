<!-- BEGIN GITRACK MANAGED INSTRUCTIONS -->

## Issue Tracking with gitrack

This project uses [`gitrack`](https://github.com/Bathtor/gitrack) for Git-native issue tracking. Issue state lives in ordinary tracked files in this repository.

### Tool Rules

- Use `gitrack` for project issue tracking.
- Prefer `--json` for agent-driven workflows.
- Use `gitrack ready --json` to find unblocked open work.
- Check `stats.skipped` in `gitrack list --json` and `gitrack ready --json` output; rerun with `-n <COUNT>` when the default limit hides needed work.
- Use `gitrack create "<title>" --body "<body>" --json` to record new work and let gitrack generate the ref when possible.
- Avoid manual `--ref` naming where possible; it is mainly for child tasks or rare cases where a human-chosen ref improves clarity.
- Use `gitrack show <ref> --json` before changing an issue.
- Use `gitrack claim <ref> --assignee <name> --json` before starting assigned work.
- Use `gitrack update <ref> --body <text> --json` to keep the current issue description and plan up to date.
- Use `gitrack link <parent> <child> --child --json` when splitting work into child issues.
- Use `gitrack link <issue> <blocker> --blocked-by --json` when one issue must wait for another.
- Use labelled `gitrack link <source> <target> --label <label> --json` for loose one-way context.
- Use comments for chronological notes, review observations, and progress history.
- Close issues with `gitrack close <ref> --reason <reason> --json`.
- Do not create parallel TODO lists when the item should be tracked as an issue.

### Git Workflow Notes

- When creating a branch for a new task, create the branch first, then claim the issue so the claim is committed on that branch.
- Before committing completed work, update the issue state first so the issue change is included in the same commit.

<!-- END GITRACK MANAGED INSTRUCTIONS -->
