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

## Suggested gitrack Workflow

### Priorities

- `0` - Immediate: drop everything and do this now.
- `1` - ASAP: finish the current task, then pick this up next before lower-priority work.
- `2` - High: important work.
- `3` - Normal: default priority for ordinary work.
- `4` - Low/Backlog: nice-to-have, polish, cleanup, or future ideas.

### Agent Workflow

#### Core Loop

1. Check ready work with `gitrack ready --json`, then inspect `stats` to see whether the result was limited.
2. Claim the selected issue with `gitrack claim <ref> --assignee <name> --json`.
3. Read the issue with `gitrack show <ref> --json`.
4. Set `status_reason = "planning"` while preparing the implementation plan.
5. Align on a concrete plan with the user before implementation.
6. Store the agreed plan in the issue body.
7. Once the user agrees, set `status_reason = "plan agreed"`.
8. Implement against the agreed plan.
9. Before handing work over for review, compare the result against the issue body and agreed plan.
10. Set `status_reason = "in review"` when ready for user review.

#### When a Branch Is Needed

Create the branch before claiming the issue so the claim is committed on that branch.

#### When Work Splits Into Children

Create child issues and link them with `gitrack link <parent> <child> --child --json`.

If the split issues have ordering constraints, link them with `gitrack link <issue> <blocker> --blocked-by --json`.

#### When New Work Is Discovered

Create the new issue, then link it back to the source issue with `gitrack link <new-ref> <source-ref> --label "discovered from" --json`.

#### Before Committing

Update issue state before committing so issue changes travel with the code or documentation changes they describe.

#### Closing Work

Only close the issue after the user agrees it is complete.

When closing, use `gitrack close <ref> --reason <reason> --json` with a concise reason such as `completed`, `won't do`, or `duplicate`.

## Scala Style

- Use a `for` loop instead of `foreach` for side-effecting iteration, unless the `foreach` body pattern-matches more than one variant.
