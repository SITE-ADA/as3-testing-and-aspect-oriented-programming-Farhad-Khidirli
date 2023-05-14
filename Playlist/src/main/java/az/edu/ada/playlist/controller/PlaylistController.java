package az.edu.ada.playlist.controller;

import az.edu.ada.playlist.entity.dto.PlaylistDTO;
import az.edu.ada.playlist.service.PlaylistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/playlist")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping("/list")
    public List<PlaylistDTO> listPlaylists() {
        return playlistService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PlaylistDTO>> getPlaylistById(@PathVariable("id") Long id) {
        Optional<PlaylistDTO> playlistDTO = playlistService.getById(id);
        if (playlistDTO.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
        } else {
            return ResponseEntity.ok(playlistDTO);
        }
    }

    @DeleteMapping("/{id}")
    public void deletePlaylist(@PathVariable("id") Long id) {
        playlistService.delete(id);
    }

    @GetMapping("/add")
    public String addPlaylist(Model model) {
        model.addAttribute("playlist", new PlaylistDTO());
        return "playlist_dir/add";
    }

    @PostMapping("/")
    public ResponseEntity<PlaylistDTO> savePlaylist(@RequestBody PlaylistDTO playlistDTO) {
        PlaylistDTO savedPlaylistDTO = playlistService.save(playlistDTO);
        return ResponseEntity.created(URI.create("/" + savedPlaylistDTO.getId())).body(savedPlaylistDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistDTO> updatePlaylist(@PathVariable("id") Long id, @RequestBody PlaylistDTO playlistDTO) {
        PlaylistDTO updatedPlaylistDTO = playlistService.update(id, playlistDTO);
        return ResponseEntity.ok(updatedPlaylistDTO);
    }

}
