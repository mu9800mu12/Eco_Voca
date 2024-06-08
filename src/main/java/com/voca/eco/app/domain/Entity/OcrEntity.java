package com.voca.eco.app.domain.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@Table(name = "OCR_INFORMATION")
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@ToString
public class OcrEntity {

    @Id
    @Column(name = "USER_ID")
    private String userId;


}
