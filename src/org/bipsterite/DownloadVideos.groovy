package org.bipsterite

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

import java.text.MessageFormat

// Loading the script configuration
Properties PROPS = new Properties()
File propertiesFile = new File("harmontown.properties")
propertiesFile.withInputStream { PROPS.load(it) }

final def USERNAME = (String) PROPS."harmontown.username",
        PASSWORD = (String) PROPS."harmontown.password",
        LOGIN_URL = (String) PROPS."harmontown.url.login",
        PODCAST_PAGE_URL = PROPS."harmontown.url.pagetemplate",
        OUTPUT_DIR = (String) PROPS."script.output.dir",
        FIRST_PAGE = Integer.parseInt((String) PROPS."script.page.start"),
        LAST_PAGE = Integer.parseInt((String) PROPS."script.page.end"),
        VIDEO_HIGH = Boolean.parseBoolean((String) PROPS."harmontown.download.videohigh"),
        VIDEO_LOW = Boolean.parseBoolean((String) PROPS."harmontown.download.videolow"),
        AUDIO = Boolean.parseBoolean((String) PROPS."harmontown.download.audio")

Connection.Response response = Jsoup.connect(LOGIN_URL)
        .data("log", USERNAME, "pwd", PASSWORD)
        .method(Connection.Method.POST)
        .execute()
def cookies = response.cookies()

for (int page = FIRST_PAGE; page <= LAST_PAGE; page++) {
    println "page ${page}"
    Document podcastPage = Jsoup.connect(MessageFormat.format((String) PODCAST_PAGE_URL, page)).cookies(cookies).get()

    Elements sectionHeaderLinks = podcastPage.select(".entry-title a")
    for (Element link : sectionHeaderLinks) {
        if (! link.text().startsWith("Video Episode")) {
            continue
        }

        String videoPageLink = link.attr("href")
        String videoDescription = videoPageLink.split('/').last()

        Document podcastVideoPage = Jsoup.connect(videoPageLink).cookies(cookies).get()

        if (AUDIO) {
            downloadFile( getAudioLink(podcastVideoPage),
                    getAudioDownloadlocation(OUTPUT_DIR, videoDescription),
                    cookies )
        }

        if (VIDEO_LOW) {
            downloadFile( getLowQualityVideoLink(podcastVideoPage),
                    getLowQualityVideoDownloadlocation(OUTPUT_DIR, videoDescription),
                    cookies )
        }

        if (VIDEO_HIGH) {
            downloadFile( getHighQualityVideoLink(podcastVideoPage),
                    getHighQualityVideoDownloadlocation(OUTPUT_DIR, videoDescription),
                    cookies )
        }
    }
}

static String getHighQualityVideoLink(Document page) { return page.select("a[title='Download']").first().attr("href") }
static String getLowQualityVideoLink(Document page) { return page.select("a[title='Download']").get(1).attr("href") }
static String getAudioLink(Document page) { return page.select("a[title='Download']").last().attr("href") }
static String getHighQualityVideoDownloadlocation(String outputDir, String videoDescription) { return "${outputDir}/${videoDescription}.mp4" }
static String getLowQualityVideoDownloadlocation(String outputDir, String videoDescription) { return "${outputDir}/${videoDescription}_LOW.mp4" }
static String getAudioDownloadlocation(String outputDir, String videoDescription) { return "${outputDir}/${videoDescription}.mp3" }

static def downloadFile(String link, String downloadLocation, def cookies) {
    println " Downloading ${link} to ${downloadLocation}"

    URLConnection urlConnection = new URL(link).openConnection()
    def cookieString = cookies.toMapString()
    urlConnection.setRequestProperty("Cookie", cookieString.substring(1, cookieString.length() - 2))
    urlConnection.connect()
    int numBytes = urlConnection.getHeaderFieldInt("Content-Length", 0)
    InputStream inputStream = urlConnection.inputStream
    File file = new File(downloadLocation)
    OutputStream out = new BufferedOutputStream(new FileOutputStream(file))

    print "  Progress: "
    int bytesRead = 0
    for (int b; (b = inputStream.read()) != -1;) {
        bytesRead++
        out.write(b)

        if (bytesRead % 50000000 == 0) {
            double progress = (double) bytesRead / numBytes * 100
            print "${(int) progress}% "
        }
    }
    println()
    out.close()
    inputStream.close()
}