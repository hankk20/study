package kr.co.hankk20.study.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardLikeDto implements Serializable {
    private String createId;
    private String modifyId;
    private LocalDate createDate;
    private LocalDate updateDate;
    private Long id;
}
