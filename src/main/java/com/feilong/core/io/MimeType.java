/*
 * Copyright (C) 2008 feilong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.core.io;

/**
 * The Enum MimeType.
 *
 * @author feilong
 * 
 * @version 1.0.8 2014年11月20日 上午10:57:48
 * @since 1.0.8
 */
public enum MimeType{

    /** The json . */
    JSON("json","application/json"),

    //****************   @see org.apache.catalina.startup.Tomcat#DEFAULT_MIME_MAPPINGS**********************************************************************

    /** The abs. */
    ABS("abs","audio/x-mpeg"),

    /** The ai. */
    AI("ai","application/postscript"),

    /** The aif. */
    AIF("aif","audio/x-aiff"),

    /** The aifc. */
    AIFC("aifc","audio/x-aiff"),

    /** The aiff. */
    AIFF("aiff","audio/x-aiff"),

    /** The aim. */
    AIM("aim","application/x-aim"),

    /** The art. */
    ART("art","image/x-jg"),

    /** The asf. */
    ASF("asf","video/x-ms-asf"),

    /** The asx. */
    ASX("asx","video/x-ms-asf"),

    /** The au. */
    AU("au","audio/basic"),

    /** The avi. */
    AVI("avi","video/x-msvideo"),

    /** The avx. */
    AVX("avx","video/x-rad-screenplay"),

    /** The bcpio. */
    BCPIO("bcpio","application/x-bcpio"),

    /** The bin. */
    BIN("bin","application/octet-stream"),

    /** The bmp. */
    BMP("bmp","image/bmp"),

    /** The body. */
    BODY("body","text/html"),

    /** The cdf. */
    CDF("cdf","application/x-cdf"),

    /** The cer. */
    CER("cer","application/pkix-cert"),

    /** The class. */
    CLASS("class","application/java"),

    /** The cpio. */
    CPIO("cpio","application/x-cpio"),

    /** The csh. */
    CSH("csh","application/x-csh"),

    /** The css. */
    CSS("css","text/css"),

    /** The dib. */
    DIB("dib","image/bmp"),

    /** The dtd. */
    DTD("dtd","application/xml-dtd"),

    /** The dv. */
    DV("dv","video/x-dv"),

    /** The dvi. */
    DVI("dvi","application/x-dvi"),

    /** The eps. */
    EPS("eps","application/postscript"),

    /** The etx. */
    ETX("etx","text/x-setext"),

    /** The exe. */
    EXE("exe","application/octet-stream"),

    /** The gif. */
    GIF("gif","image/gif"),

    /** The gtar. */
    GTAR("gtar","application/x-gtar"),

    /** The gz. */
    GZ("gz","application/x-gzip"),

    /** The hdf. */
    HDF("hdf","application/x-hdf"),

    /** The hqx. */
    HQX("hqx","application/mac-binhex40"),

    /** The htc. */
    HTC("htc","text/x-component"),

    /** The htm. */
    HTM("htm","text/html"),

    /** The html. */
    HTML("html","text/html"),

    /** The ief. */
    IEF("ief","image/ief"),

    /** The jad. */
    JAD("jad","text/vnd.sun.j2me.app-descriptor"),

    /** The jar. */
    JAR("jar","application/java-archive"),

    /** The java. */
    JAVA("java","text/x-java-source"),

    /** The jnlp. */
    JNLP("jnlp","application/x-java-jnlp-file"),

    /** The jpe. */
    JPE("jpe","image/jpeg"),

    /** The jpeg. */
    JPEG("jpeg","image/jpeg"),

    /** The jpg. */
    JPG("jpg","image/jpeg"),

    /** The js. */
    JS("js","application/javascript"),

    /** The jsf. */
    JSF("jsf","text/plain"),

    /** The jspf. */
    JSPF("jspf","text/plain"),

    /** The kar. */
    KAR("kar","audio/midi"),

    /** The latex. */
    LATEX("latex","application/x-latex"),

    /** The M3 u. */
    M3U("m3u","audio/x-mpegurl"),

    /** The mac. */
    MAC("mac","image/x-macpaint"),

    /** The man. */
    MAN("man","text/troff"),

    /** The mathml. */
    MATHML("mathml","application/mathml+xml"),

    /** The me. */
    ME("me","text/troff"),

    /** The mid. */
    MID("mid","audio/midi"),

    /** The midi. */
    MIDI("midi","audio/midi"),

    /** The mif. */
    MIF("mif","application/x-mif"),

    /** The mov. */
    MOV("mov","video/quicktime"),

    /** The movie. */
    MOVIE("movie","video/x-sgi-movie"),

    /** The M p1. */
    MP1("mp1","audio/mpeg"),

    /** The M p2. */
    MP2("mp2","audio/mpeg"),

    /** The M p3. */
    MP3("mp3","audio/mpeg"),

    /** The M p4. */
    MP4("mp4","video/mp4"),

    /** The mpa. */
    MPA("mpa","audio/mpeg"),

    /** The mpe. */
    MPE("mpe","video/mpeg"),

    /** The mpeg. */
    MPEG("mpeg","video/mpeg"),

    /** The mpega. */
    MPEGA("mpega","audio/x-mpeg"),

    /** The mpg. */
    MPG("mpg","video/mpeg"),

    /** The MP v2. */
    MPV2("mpv2","video/mpeg2"),

    /** The nc. */
    NC("nc","application/x-netcdf"),

    /** The oda. */
    ODA("oda","application/oda"),

    /** The ogx. */
    OGX("ogx","application/ogg"),

    /** The ogv. */
    OGV("ogv","video/ogg"),

    /** The oga. */
    OGA("oga","audio/ogg"),

    /** The ogg. */
    OGG("ogg","audio/ogg"),

    /** The spx. */
    SPX("spx","audio/ogg"),

    /** The flac. */
    FLAC("flac","audio/flac"),

    /** The anx. */
    ANX("anx","application/annodex"),

    /** The axa. */
    AXA("axa","audio/annodex"),

    /** The axv. */
    AXV("axv","video/annodex"),

    /** The xspf. */
    XSPF("xspf","application/xspf+xml"),

    /** The pbm. */
    PBM("pbm","image/x-portable-bitmap"),

    /** The pct. */
    PCT("pct","image/pict"),

    /** The pdf. */
    PDF("pdf","application/pdf"),

    /** The pgm. */
    PGM("pgm","image/x-portable-graymap"),

    /** The pic. */
    PIC("pic","image/pict"),

    /** The pict. */
    PICT("pict","image/pict"),

    /** The pls. */
    PLS("pls","audio/x-scpls"),

    /** The png. */
    PNG("png","image/png"),

    /** The pnm. */
    PNM("pnm","image/x-portable-anymap"),

    /** The pnt. */
    PNT("pnt","image/x-macpaint"),

    /** The ppm. */
    PPM("ppm","image/x-portable-pixmap"),

    /** The ps. */
    PS("ps","application/postscript"),

    /** The psd. */
    PSD("psd","image/vnd.adobe.photoshop"),

    /** The qt. */
    QT("qt","video/quicktime"),

    /** The qti. */
    QTI("qti","image/x-quicktime"),

    /** The qtif. */
    QTIF("qtif","image/x-quicktime"),

    /** The ras. */
    RAS("ras","image/x-cmu-raster"),

    /** The rdf. */
    RDF("rdf","application/rdf+xml"),

    /** The rgb. */
    RGB("rgb","image/x-rgb"),

    /** The rm. */
    RM("rm","application/vnd.rn-realmedia"),

    /** The roff. */
    ROFF("roff","text/troff"),

    /** The rtf. */
    RTF("rtf","application/rtf"),

    /** The rtx. */
    RTX("rtx","text/richtext"),

    /** The sh. */
    SH("sh","application/x-sh"),

    /** The shar. */
    SHAR("shar","application/x-shar"),
    ///*"SHTML"("/*"shtml"","text/x-server-parsed-html*/"),
    /** The sit. */
    SIT("sit","application/x-stuffit"),

    /** The snd. */
    SND("snd","audio/basic"),

    /** The src. */
    SRC("src","application/x-wais-source"),

    /** The S v4 cpio. */
    SV4CPIO("sv4cpio","application/x-sv4cpio"),

    /** The S v4 crc. */
    SV4CRC("sv4crc","application/x-sv4crc"),

    /** The svg. */
    SVG("svg","image/svg+xml"),

    /** The svgz. */
    SVGZ("svgz","image/svg+xml"),

    /** The swf. */
    SWF("swf","application/x-shockwave-flash"),

    /** The t. */
    T("t","text/troff"),

    /** The tar. */
    TAR("tar","application/x-tar"),

    /** The tcl. */
    TCL("tcl","application/x-tcl"),

    /** The tex. */
    TEX("tex","application/x-tex"),

    /** The texi. */
    TEXI("texi","application/x-texinfo"),

    /** The texinfo. */
    TEXINFO("texinfo","application/x-texinfo"),

    /** The tif. */
    TIF("tif","image/tiff"),

    /** The tiff. */
    TIFF("tiff","image/tiff"),

    /** The tr. */
    TR("tr","text/troff"),

    /** The tsv. */
    TSV("tsv","text/tab-separated-values"),

    /** The txt. */
    TXT("txt","text/plain"),

    /** The ulw. */
    ULW("ulw","audio/basic"),

    /** The ustar. */
    USTAR("ustar","application/x-ustar"),

    /** The vxml. */
    VXML("vxml","application/voicexml+xml"),

    /** The xbm. */
    XBM("xbm","image/x-xbitmap"),

    /** The xht. */
    XHT("xht","application/xhtml+xml"),

    /** The xhtml. */
    XHTML("xhtml","application/xhtml+xml"),

    /** The xml. */
    XML("xml","application/xml"),

    /** The xpm. */
    XPM("xpm","image/x-xpixmap"),

    /** The xsl. */
    XSL("xsl","application/xml"),

    /** The xslt. */
    XSLT("xslt","application/xslt+xml"),

    /** The xul. */
    XUL("xul","application/vnd.mozilla.xul+xml"),

    /** The xwd. */
    XWD("xwd","image/x-xwindowdump"),

    /** The vsd. */
    VSD("vsd","application/vnd.visio"),

    /** The wav. */
    WAV("wav","audio/x-wav"),

    /** The wbmp. */
    WBMP("wbmp","image/vnd.wap.wbmp"),

    /** The wml. */
    WML("wml","text/vnd.wap.wml"),

    /** The wmlc. */
    WMLC("wmlc","application/vnd.wap.wmlc"),

    /** The wmls. */
    WMLS("wmls","text/vnd.wap.wmlsc"),

    /** The wmlscriptc. */
    WMLSCRIPTC("wmlscriptc","application/vnd.wap.wmlscriptc"),

    /** The wmv. */
    WMV("wmv","video/x-ms-wmv"),

    /** The wrl. */
    WRL("wrl","model/vrml"),

    /** The wspolicy. */
    WSPOLICY("wspolicy","application/wspolicy+xml"),

    /** The z. */
    Z("Z","application/x-compress"),

    /** The zip. */
    ZIP("zip","application/zip"),

    //****************************************************************************************************

    // MS Office
    /** The doc. */
    DOC("doc","application/msword"),

    /** The dot. */
    DOT("dot","application/msword"),

    /** The docx. */
    DOCX("docx","application/vnd.openxmlformats-officedocument.wordprocessingml.document"),

    /** The dotx. */
    DOTX("dotx","application/vnd.openxmlformats-officedocument.wordprocessingml.template"),

    /** The docm. */
    DOCM("docm","application/vnd.ms-word.document.macroEnabled.12"),

    /** The dotm. */
    DOTM("dotm","application/vnd.ms-word.template.macroEnabled.12"),

    /** The xls. */
    XLS("xls","application/vnd.ms-excel"),

    /** The xlt. */
    XLT("xlt","application/vnd.ms-excel"),

    /** The xla. */
    XLA("xla","application/vnd.ms-excel"),

    /** The xlsx. */
    XLSX("xlsx","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),

    /** The xltx. */
    XLTX("xltx","application/vnd.openxmlformats-officedocument.spreadsheetml.template"),

    /** The xlsm. */
    XLSM("xlsm","application/vnd.ms-excel.sheet.macroEnabled.12"),

    /** The xltm. */
    XLTM("xltm","application/vnd.ms-excel.template.macroEnabled.12"),

    /** The xlam. */
    XLAM("xlam","application/vnd.ms-excel.addin.macroEnabled.12"),

    /** The xlsb. */
    XLSB("xlsb","application/vnd.ms-excel.sheet.binary.macroEnabled.12"),

    /** The ppt. */
    PPT("ppt","application/vnd.ms-powerpoint"),

    /** The pot. */
    POT("pot","application/vnd.ms-powerpoint"),

    /** The pps. */
    PPS("pps","application/vnd.ms-powerpoint"),

    /** The ppa. */
    PPA("ppa","application/vnd.ms-powerpoint"),

    /** The pptx. */
    PPTX("pptx","application/vnd.openxmlformats-officedocument.presentationml.presentation"),

    /** The potx. */
    POTX("potx","application/vnd.openxmlformats-officedocument.presentationml.template"),

    /** The ppsx. */
    PPSX("ppsx","application/vnd.openxmlformats-officedocument.presentationml.slideshow"),

    /** The ppam. */
    PPAM("ppam","application/vnd.ms-powerpoint.addin.macroEnabled.12"),

    /** The pptm. */
    PPTM("pptm","application/vnd.ms-powerpoint.presentation.macroEnabled.12"),

    /** The potm. */
    POTM("potm","application/vnd.ms-powerpoint.presentation.macroEnabled.12"),

    /** The ppsm. */
    PPSM("ppsm","application/vnd.ms-powerpoint.slideshow.macroEnabled.12"),

    // Open Office
    /** The odt. */
    ODT("odt","application/vnd.oasis.opendocument.text"),

    /** The ott. */
    OTT("ott","application/vnd.oasis.opendocument.text-template"),

    /** The oth. */
    OTH("oth","application/vnd.oasis.opendocument.text-web"),

    /** The odm. */
    ODM("odm","application/vnd.oasis.opendocument.text-master"),

    /** The odg. */
    ODG("odg","application/vnd.oasis.opendocument.graphics"),

    /** The otg. */
    OTG("otg","application/vnd.oasis.opendocument.graphics-template"),

    /** The odp. */
    ODP("odp","application/vnd.oasis.opendocument.presentation"),

    /** The otp. */
    OTP("otp","application/vnd.oasis.opendocument.presentation-template"),

    /** The ods. */
    ODS("ods","application/vnd.oasis.opendocument.spreadsheet"),

    /** The ots. */
    OTS("ots","application/vnd.oasis.opendocument.spreadsheet-template"),

    /** The odc. */
    ODC("odc","application/vnd.oasis.opendocument.chart"),

    /** The odf. */
    ODF("odf","application/vnd.oasis.opendocument.formula"),

    /** The odb. */
    ODB("odb","application/vnd.oasis.opendocument.database"),

    /** The odi. */
    ODI("odi","application/vnd.oasis.opendocument.image"),

    /** The oxt. */
    OXT("oxt","application/vnd.openofficeorg.extension");

    //****************************************************************************************

    /** 扩展名. */
    private String extension;

    /** mime. */
    private String mime;

    /**
     * The Constructor.
     *
     * @param extension
     *            the extension
     * @param mime
     *            the mime
     */
    private MimeType(String extension, String mime){
        this.extension = extension;
        this.mime = mime;
    }

    /**
     * 获得 扩展名.
     *
     * @return the extension
     */
    public String getExtension(){
        return extension;
    }

    /**
     * 设置 扩展名.
     *
     * @param extension
     *            the extension to set
     */
    public void setExtension(String extension){
        this.extension = extension;
    }

    /**
     * 获得 mime.
     *
     * @return the mime
     */
    public String getMime(){
        return mime;
    }

    /**
     * 设置 mime.
     *
     * @param mime
     *            the mime to set
     */
    public void setMime(String mime){
        this.mime = mime;
    }
}