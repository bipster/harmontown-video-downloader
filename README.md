# Harmontown Video Downloader #

This is a rough script for downloading videos from the Harmontown.com website.

## Be Good

### Pay the subscription fee
You will need to supply credentials for an active subscription via a properties file; do not share/borrow them.

### Do not make this this multi-threaded
For the benefit of not just the community's but your own ability to download videos at a reasonable pace, [please respect](https://www.reddit.com/r/Harmontown/comments/d2dxnb/harmontown_is_ending/):
> "Planning on downloading everything? Make sure you have about 220GB of space available, and only download one file at a time to keep the server fast for everyone."
 
## Overview

* This is a Groovy script that requires Java be installed on your computer.  If you are on a Mac, this should work as-is.
* This will require more than 220GB of space available. (TODO: update with exact number)
* This requires an active subscription to [Harmontown.com](http://www.harmontown.com)
* If the script dies for some reason, you will be able to restart the script from where you left off; you will not have to start from scratch

## Preparing the Script
Copy `harmontown.properties.template` to `harmontown.properties` and fill in these properties:
* `script.output.dir` -- The directory path on your computer whereto you want these files downloaded. `/example/path/format/`
If your filepath has spaces in it, put this in double-quotes.
* `harmontown.username` -- Your login username
* `harmontown.password` -- Your login password

You can leave the other properties w/ with their defaults.

## Running the Script
(TODO)

## Restarting the Script

By default, the script starts at the first page of podcasts, downloading files one by one until it gets to the last page.

If you need to stop your script at some point, or it crashes, look through the output to determine which page the script
had stopped.  Change these properties in your `harmontown.properties` and restart the script:

* `script.page.start` -- Change to the page from which you would like the script to start
* `script.page.end` -- If desired, change to the page on which you would like the script to end.  Useful for if you
only want to download portions of the archive, or only want to download a single page of files (e.g. if you find one is
corrupted)
