package com.yjq.coinmaster.mvp.ui.news.biworld;

public class BiWorldData {

    private StatusType status = StatusType.STATUS_CONTRACT;

    public int newsflash_id;
    public String content;
    public String source;
    public String link_title;
    public String link;
    public long issue_time;
    public int rank;
    public int img_path_type;
    public int bull_vote;
    public int bad_vote;
    public int is_promotion;
    public String title;
    public String classStyle;
    public int voted_type;
    public int content_length;

    public void setStatus(StatusType statusType) {
        this.status = statusType;
    }

    public StatusType getStatus() {
        return status;
    }
}
