package az.edu.ada.playlist.entity.dto;

import az.edu.ada.playlist.entity.Playlist;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDTO {
    private long id;

    @NotBlank
    @Size(min = 4, max = 16)
    private String name;

    @NotBlank
    private String description;
}
