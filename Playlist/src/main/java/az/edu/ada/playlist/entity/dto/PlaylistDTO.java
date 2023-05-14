package az.edu.ada.playlist.entity.dto;

import az.edu.ada.playlist.entity.Playlist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistDTO {
    private long id;
    private String name;
    private String description;
}
