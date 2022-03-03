package com.themusicshop.app.persistence.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
@Table()
public class Vinyl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String composerName;
    private String category;
    private String musicName;
    private String album;

    public Vinyl(int id, String composerName, String category, String musicName, String album) {
        this.id = id;
        this.composerName = composerName;
        this.category = category;
        this.musicName = musicName;
        this.album = album;
    }
}
