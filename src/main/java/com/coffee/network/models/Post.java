package com.coffee.network.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private int id;

  @Column(name = "content")
  @Getter
  @Setter
  private String content;

  @Column(name = "created_at", insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  @Getter
  private Date createdAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  @Getter
  @Setter
  private User user;

}
