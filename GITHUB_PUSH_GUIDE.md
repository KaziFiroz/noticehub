# 🐙 Pushing NoticeHub to GitHub — Step by Step

This guide walks through publishing this project safely: no secrets, no `node_modules`,
no build artifacts.

---

## 0. One-time sanity check before you push anything

From the project root, run:
```bash
grep -rn "jwt.secret\s*=\s*[A-Za-z0-9]" backend/noticehub/src/main/resources/application.properties
```
You should see the value is `${JWT_SECRET:local-dev-placeholder-...}` — an environment
variable reference, **not** a literal secret. If you ever hand-edit `application.properties`
back to a real hardcoded password or secret, undo that before committing.

Also double check no `.env` file is staged (it shouldn't exist yet if you haven't created one,
and `.gitignore` already excludes it):
```bash
git status
```

---

## 1. Create the repository on GitHub

1. Go to https://github.com/new
2. **Repository name:** `noticehub` (or `NoticeHub`)
3. **Description** — paste this in:

   > Full-stack notice board app for colleges (Spring Boot + React + MySQL) with JWT auth and role-based access control (Admin/Faculty/Student).

4. **Visibility:** Public (so the interviewer can view it without an invite)
5. Do **NOT** check "Add a README" / "Add .gitignore" / "Add a license" — you already have these locally and don't want conflicting files.
6. Click **Create repository** and keep the page open — GitHub will show you the remote URL, e.g. `https://github.com/<your-username>/noticehub.git`

---

## 2. Initialize git locally and make the first commit

From the project root (the folder containing `backend/`, `frontend/`, `README.md`):

```bash
cd NoticeHub
git init
git add .
git status   # <-- review this list before committing
```

**Check the `git status` output carefully.** You should NOT see:
- `frontend/node_modules/...`
- `backend/noticehub/target/...`
- any `.env` file
- `.idea/`, `.vscode/`, `.classpath`, etc.

If any of those appear, your `.gitignore` isn't being picked up — stop and fix it
(see the Troubleshooting section below) before committing.

Once the list looks clean:
```bash
git commit -m "Initial commit: NoticeHub full-stack app"
```

---

## 3. Connect to GitHub and push

```bash
git branch -M main
git remote add origin https://github.com/<your-username>/noticehub.git
git push -u origin main
```

If prompted for credentials and you have 2FA enabled on GitHub, use a
[Personal Access Token](https://github.com/settings/tokens) instead of your password,
or push via SSH if you already have an SSH key set up with GitHub.

---

## 4. Verify on GitHub

Open `https://github.com/<your-username>/noticehub` in a browser and confirm:
- [ ] `node_modules/` and `target/` are **not** in the file list
- [ ] `application.properties` shows `${JWT_SECRET:...}` / `${DB_PASSWORD:...}`, not real values
- [ ] No `.env` file is present
- [ ] `README.md` renders correctly on the repo homepage

---

## 5. What to send the interviewer

Just the repository URL:
```
https://github.com/<your-username>/noticehub
```
The `README.md` on the homepage explains what the project does, the tech stack, and how
to run it — that's usually all an interviewer needs to skim before a discussion.

---

## Troubleshooting

**"`node_modules` still shows up in `git status` even though I have a `.gitignore`"**
This usually means git already started tracking it in a previous, undone `git init`.
Fix:
```bash
git rm -r --cached frontend/node_modules backend/noticehub/target
git commit -m "Remove accidentally tracked build artifacts"
```

**"I already pushed and now realize a real secret is in the history"**
Changing the file and committing again is *not* enough — the old value still exists in
git history and is visible to anyone. You'd need to either:
- rotate/change that secret immediately (treat it as compromised), and
- rewrite history with `git filter-repo` or delete and recreate the repo.
For a placeholder/demo secret like the one in this project, rotating is usually simpler
than rewriting history — but it's good to know the difference for real projects.
