package az.edu.ada.playlist.service.implementation;

import az.edu.ada.playlist.entity.Playlist;
import az.edu.ada.playlist.entity.dto.PlaylistDTO;
import az.edu.ada.playlist.mapper.PlaylistMapper;
import az.edu.ada.playlist.repository.PlaylistRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceImplTest {

    private PlaylistServiceImpl underTest;
    @Mock
    private PlaylistRepository playlistRepository;

    @Mock
    private PlaylistMapper playlistMapper;

    @BeforeEach
    void setUp() {
        playlistMapper = Mockito.mock(PlaylistMapper.class);
        underTest = new PlaylistServiceImpl(playlistRepository, playlistMapper);
    }

    @Test
    void SuccessList() {
        // given
        List<Playlist> playlists = new ArrayList<>();
        playlists.add(new Playlist(1, "Test Playlist", "Testing Playlist"));
        when(playlistRepository.findAll()).thenReturn(playlists);

        List<PlaylistDTO> expected = new ArrayList<>();
        expected.add(new PlaylistDTO(1, "Test Playlist", "Testing Playlist"));

        when(playlistMapper.toPlaylistDTOList(playlists)).thenReturn(expected);

        // when
        List<PlaylistDTO> result = underTest.list();

        // then
        assertEquals(expected, result);
        verify(playlistRepository).findAll();
        verify(playlistMapper).toPlaylistDTOList(playlists);
    }


    @Test
    void delete() {
        // given
        long playlistId = 1;

        // when
        underTest.delete(playlistId);

        // then
        verify(playlistRepository).deleteById(playlistId);
    }


    @Test
    void save() {
        // given
        PlaylistDTO playlistDTO = new PlaylistDTO(1, "Test Playlist", "Testing Playlist");
        Playlist playlist = new Playlist(1, "Test Playlist", "Testing Playlist");
        when(playlistMapper.toEntity(playlistDTO)).thenReturn(playlist);
        when(playlistRepository.save(playlist)).thenReturn(playlist);
        when(playlistMapper.toDto(playlist)).thenReturn(playlistDTO);

        // when
        PlaylistDTO result = underTest.save(playlistDTO);

        // then
        assertEquals(playlistDTO, result);
        verify(playlistMapper).toEntity(playlistDTO);
        verify(playlistRepository).save(playlist);
        verify(playlistMapper).toDto(playlist);
    }

}