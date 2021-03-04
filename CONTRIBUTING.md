
# Contributing to eReferendum

First off, thanks for taking the time to contribute! 🎉👍

Before contributing to this repository, please read through this documentation and use it as a guideline for contributing. 

Only team members (listed in README.md) are allowed to contribute!

## Table of contents

- [Project structure](#project-structure)
- [Version control](#version-control)
- [Setting up the project locally](#setting-up-the-project-locally)
- [What to contribute?](#what-to-contribute)
 
## Project structure

- Microservices are listed in [README.md](./README.md#microservices)
- Each microservice lives in its own folder. Some files are shared in the root folder (.gitignore, .docker-compose.yml etc.)

## Version control

### Branching

- **master** is a production-ready branch. Use **develop** branch to deliver latest development changes for the next release of eReferendum.
- Each microservice has its own branch named accordingly.
- When finished implementing a new feature create a pull request to merge your branch into develop, and wait for approval or a review of your request. **Never push directly to master or develop**

### Committing

- Commit regularly and only when source code can be successfully built and ran
- Also, make sure to lint and format the code, and run the tests before committing your
changes.
- Write semantic git commit messages in the following format `<emoji> <subject>`

```
📝 Update README.md
^  ^---------------^
|     |
|     +-> Summary in present tense.
|
+---> emoji: 🐛 - bugfix, 📝 - documentation, ♻️ - refactoring...
```
#### Which Emoji to Use?

|Emoji|Commit type|Example message|
|--|--|--|
|🔖|Version Tag|`🔖 Bump to version 2.0`|
|🐛|Bugfix|`🐛 Fix user.name null reference`|
|🔒|Security Fix|`🔒 Encrypt user password`|
|♻️|Refactoring|`♻️ Rename firstName -> name`|
|📝|Documentation|`📝 Include MIT license liability`|
|🏃‍♂️|Performance|`🏃‍♂️ Remove unnecessary callback`|
|♿|Accessibility|`♿ Add aria labels`|
|🎨|Cosmetic|`🎨 Add styles to homepage`|
|🚨|Tests|`🚨 Add new tests for User class`|
|💩|Deprecation|`💩 Remove deprecated lifecycle method`|
|🗑️|Removal|`🗑️ Delete unnecessary resources`|
|🚧|Work In Progress|`🚧 Implement n functionality`|
|⬆️|Adding Dependency|`⬆️ Include Jupiter library`|
|⬇️|Removing dependency|`⬇️ Remove Postgres library`|

**Don't see appropriate emoji listed?** Be creative! `🎉 Project Init`

## Setting up the project locally

TODO

## What to contribute?

- Refer to repository [projects tab](https://github.com/jvrlic/Seminar_Sa_blesavim_imenom/projects) to check active and inactive tasks. 
- Tasks will be written down as GitHub issues
- Each issue will be assigned to a single person
- Before closing an issue, assign at least one project member for a review.
