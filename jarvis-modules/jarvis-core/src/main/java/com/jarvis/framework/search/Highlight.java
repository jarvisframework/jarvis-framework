package com.jarvis.framework.search;

/**
 *
 * @author qiucs
 * @version 1.0.0 2023年1月30日
 */
public class Highlight {

    private String preTags = "<em class=\"highlight\">";

    private String postTags = "</em>";

    private String[] fields;

    private Integer fragmentSize = 64;

    private Integer numberOfFragments = 1;

    private Integer noMatchSize = 64;

    private boolean requireFieldMatch = false;

    private String keyword;

    public Highlight() {
    }

    public Highlight(String keyword, String... fields) {
        this.keyword = keyword;
        this.fields = fields;
    }

    public Highlight(String keyword, boolean requireFieldMatch, String... fields) {
        this.keyword = keyword;
        this.requireFieldMatch = requireFieldMatch;
        this.fields = fields;
    }

    public Highlight(boolean requireFieldMatch, String... fields) {
        this.requireFieldMatch = requireFieldMatch;
        this.fields = fields;
    }

    /**
     * @return the preTags
     */
    public String getPreTags() {
        return preTags;
    }

    /**
     * @param preTags the preTags to set
     */
    public void setPreTags(String preTags) {
        this.preTags = preTags;
    }

    /**
     * @return the postTags
     */
    public String getPostTags() {
        return postTags;
    }

    /**
     * @param postTags the postTags to set
     */
    public void setPostTags(String postTags) {
        this.postTags = postTags;
    }

    /**
     * @return the fields
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(String[] fields) {
        this.fields = fields;
    }

    /**
     * @return the fragmentSize
     */
    public Integer getFragmentSize() {
        return fragmentSize;
    }

    /**
     * @param fragmentSize the fragmentSize to set
     */
    public void setFragmentSize(Integer fragmentSize) {
        this.fragmentSize = fragmentSize;
    }

    /**
     * @return the numberOfFragments
     */
    public Integer getNumberOfFragments() {
        return numberOfFragments;
    }

    /**
     * @param numberOfFragments the numberOfFragments to set
     */
    public void setNumberOfFragments(Integer numberOfFragments) {
        this.numberOfFragments = numberOfFragments;
    }

    /**
     * @return the noMatchSize
     */
    public Integer getNoMatchSize() {
        return noMatchSize;
    }

    /**
     * @param noMatchSize the noMatchSize to set
     */
    public void setNoMatchSize(Integer noMatchSize) {
        this.noMatchSize = noMatchSize;
    }

    /**
     * @return the requireFieldMatch
     */
    public boolean isRequireFieldMatch() {
        return requireFieldMatch;
    }

    /**
     * @param requireFieldMatch the requireFieldMatch to set
     */
    public void setRequireFieldMatch(boolean requireFieldMatch) {
        this.requireFieldMatch = requireFieldMatch;
    }

    /**
     * @return the keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * @param keyword the keyword to set
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

}
