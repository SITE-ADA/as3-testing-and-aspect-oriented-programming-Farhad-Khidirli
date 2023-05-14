package az.edu.ada.playlist.controller;

import az.edu.ada.playlist.entity.dto.PlaylistDTO;
import az.edu.ada.playlist.service.PlaylistService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlaylistController.class)
public class PlaylistControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlaylistService playlistService;

    @Test
    public void listPlaylists() throws Exception {
        // given
        List<PlaylistDTO> playlists = Arrays.asList(
                new PlaylistDTO(1, "Playlist 1", "Playlist 1 Description"),
                new PlaylistDTO(2, "Playlist 2", "Playlist 2 Description")
        );
        when(playlistService.list()).thenReturn(playlists);

        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/playlist/list");
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();


        // then
        String responseBody = result.getResponse().getContentAsString();
        List<PlaylistDTO> responsePlaylists = new ObjectMapper().readValue(responseBody, new TypeReference<>() {
        });
        assertEquals(playlists, responsePlaylists);
    }
}