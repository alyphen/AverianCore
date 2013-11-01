AverianCore
===========

Averian's core plugin.

Covers character cards, roll info, classes, experience, etcetera.

Compilation
===========

First clone the repository using git:

```
git clone https://github.com/Averian/AverianCore.git;
```

Then use Maven to compile it:

```
cd AverianCore;
mvn clean install;
```

This will create a ready-to-run JAR inside the /target directory.

If you don't have Maven, install it using your OS's package manager (Homebrew reccommended for OS X users, Yum pre-installed on Fedora, Apt on Debian-based distributions, or Pacman on Arch-based distributions).
If this is not possible, get it [here.](http://maven.apache.org/download.cgi)

If you don't have Git, again, your OS's package manager is probably the best choice.
If this isn't possible, Git can be obtained [here.](http://git-scm.com/downloads)

Obtaining pre-compiled JARs
===========

Click on the "releases" tab at the top of the screen.
This will show you any JARs I've tagged as releases, which should be stable to run.