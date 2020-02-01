# Harmontown Video Downloader #

This is a rough script for downloading videos from the Harmontown.com website.

Output snippet:
```
page 6
    Downloading https://download.harmontown.com/video/harmontown-2019-04-01-final.mp4 to /Users/bipster/Desktop/temp/video-episode-328-jeffs-joke-corner.mp4
     Progress: 3% 6% 9% 12% 15% 19% 22% 25% 28% 31% 35% 38% 41% 44% 47% 51% 54% 57% 60% 63% 66% 70% 73% 76% 79% 82% 86% 89% 92% 95% 98% 
    Downloading https://download.harmontown.com/video/harmontown-2019-03-25-final.mp4 to /Users/bipster/Desktop/temp/video-episode-327-a-cardigan-with-stab-holes.mp4
     Progress: 3% 7% 11% ...
```

## Overview

* This is a Groovy script that requires Java be installed on your computer.  If you are on a Mac, this should work as-is.
* This will require more than 220GB of space available. (TODO: update with exact number)
* This requires an active subscription to [Harmontown.com](http://www.harmontown.com)
* If the script dies for some reason, you will be able to restart the script from where you left off; you will not have to start from scratch
* This only downloads the full-resolution videos.  It skips both low-resolution videos and audio files

**Note:** As of October 21, 2019, 66 of the 99 archive pages at Harmontown.com contain 227 videos, totaling ~237.5 GB.  As-is, the script will scrape the first 98 archive pages for videos -- quickly skipping over any that lack videos -- which should be sufficient until .. the End :(  The first attempt at a video episode was Episode 117 - "Debbie Request Permission To Do Dallas"

## Be Good

### Pay the subscription fee
You will need to supply credentials for an active subscription via a properties file; do not share/borrow them.

### Do not make this this multi-threaded
For the benefit of not just the community's but your own ability to download videos at a reasonable pace, [please respect](https://www.reddit.com/r/Harmontown/comments/d2dxnb/harmontown_is_ending/):
> "Planning on downloading everything? Make sure you have about 220GB of space available, and only download one file at a time to keep the server fast for everyone."

## Preparing the Script
Download the script using the green "Clone or download" button (Download zip) and unpack into a local directory.

Copy `harmontown.properties.template` to `harmontown.properties` and fill in these properties:
* `script.output.dir` -- The directory path on your computer whereto you want these files downloaded. `/example/path/format/`
If your filepath has spaces in it, put this in double-quotes.  Use this forward-slash format even if you are on a Windows machine.
* `harmontown.username` -- Your login username
* `harmontown.password` -- Your login password
* `harmontown.download.*` -- change to `true` for the ones you want

You can leave the other properties w/ with their defaults.

## Running the Script
If you are on the Mac, the easiest approach might be:
1. Open Terminal
2. `cd [DIRECTORY OF YOUR UNPACKED ZIP]`
3. `sh harmontown-downloader.sh` This is a very simple -- and probably unnecessary -- wrapper around the Java command inside the file (which you can run directly).

If you are on Windows (and have a recent Java installed!):
1. Open Command Prompt
2. `cd [DIRECTORY OF YOUR UNPACKED ZIP]`
3. Run: `java -classpath %cd%/lib/*;%cd% groovy.ui.GroovyMain src/org/bipsterite/DownloadVideos.groovy` -- This is the Windows version of the Java command inside `harmontown-downloader.sh`

## Restarting the Script

By default, the script starts at the first page of podcasts, downloading files one by one until it gets to the last page.

If you need to stop your script at some point, or it crashes, look through the output to determine which page the script
had stopped.  Change these properties in your `harmontown.properties` and restart the script:

* `script.page.start` -- Change to the page from which you would like the script to start
* `script.page.end` -- If desired, change to the page on which you would like the script to end.  Useful for if you
only want to download portions of the archive, or only want to download a single page of files (e.g. if you find one is
corrupted)
