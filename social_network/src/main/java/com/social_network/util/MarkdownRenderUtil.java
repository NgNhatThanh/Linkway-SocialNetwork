package com.social_network.util;

import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class MarkdownRenderUtil {

    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownRenderUtil() {

        MutableDataSet options = new MutableDataSet();

        // Enable the TOC and AnchorLink extensions
        options.set(Parser.EXTENSIONS, Arrays.asList(TocExtension.create(), AnchorLinkExtension.create()));

        options.set(TocExtension.LIST_CLASS, "toc-list");
        options.set(TocExtension.TITLE, "Table of Contents");
        options.set(TocExtension.IS_NUMBERED, true); // Optional numbering for TOC
        options.set(TocExtension.IS_HTML, true); // Generates the TOC in HTML format
        options.set(TocExtension.LEVELS, 3); // Depth of headers in the TOC (optional)

        options.set(AnchorLinkExtension.ANCHORLINKS_WRAP_TEXT, false);
        options.set(AnchorLinkExtension.ANCHORLINKS_SET_ID, true);  // Adds `id` attributes to headings

        this.parser = Parser.builder(options)
                .extensions(List.of(TablesExtension.create()))
                .build();
        this.renderer = HtmlRenderer.builder(options)
                .extensions(List.of(TablesExtension.create()))
                .build();
    }

    public String convertToHtml(String markdown) {
        return renderer.render(parser.parse(markdown));
    }

}