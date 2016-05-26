package com.hynl.weibo.hynlentity;

/**
 * Created by tt6000 on 2016/5/6.
 */
public class Reminds {
            int status;
           int follower;
            int cmt;
            int dm;
           int mention_status;
            int mention_cmt;
    int group;
    int private_group;
    int notice;
    int invite;
    int badge;
    int photo;
    int msgbox;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPrivate_group() {

        return private_group;
    }

    public void setPrivate_group(int private_group) {
        this.private_group = private_group;
    }

    public int getPhoto() {

        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getNotice() {

        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }

    public int getMsgbox() {

        return msgbox;
    }

    public void setMsgbox(int msgbox) {
        this.msgbox = msgbox;
    }

    public int getMention_status() {

        return mention_status;
    }

    public void setMention_status(int mention_status) {
        this.mention_status = mention_status;
    }

    public int getMention_cmt() {

        return mention_cmt;
    }

    public void setMention_cmt(int mention_cmt) {
        this.mention_cmt = mention_cmt;
    }

    public int getInvite() {

        return invite;
    }

    public void setInvite(int invite) {
        this.invite = invite;
    }

    public int getGroup() {

        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getFollower() {

        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getDm() {
        return dm;
    }

    public void setDm(int dm) {
        this.dm = dm;
    }

    public int getCmt() {

        return cmt;
    }

    public void setCmt(int cmt) {
        this.cmt = cmt;
    }

    public int getBadge() {

        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }


}
