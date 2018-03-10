package com.zk.wanandroid.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @description: 搜索历史记录保存在数据库中的实体类
 * @author: zhukai
 * @date: 2018/3/9 14:20
 */
@Entity
public class SearchHistory {

    @Id(autoincrement = true)
    private Long id;
    private String name; // 文章名称
    private Date date; // 搜索日期
    @Generated(hash = 1880161766)
    public SearchHistory(Long id, String name, Date date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }
    @Generated(hash = 1905904755)
    public SearchHistory() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
