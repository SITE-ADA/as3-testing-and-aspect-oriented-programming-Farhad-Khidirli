package az.edu.ada.playlist.repository;

import az.edu.ada.playlist.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
//    @Query(value = "SELECT * FROM playlists INNER JOIN song_and_playlist ON song_and_playlist.p_id = playlists.id WHERE song_and_playlist.s_id = :id;", nativeQuery = true)
//    Iterable<Playlist> getPlaylistsBySongId(@Param("id") Long id);

//    @Query(value = "SELECT playlists.name AS playlist_name, SUM(songs.duration) AS total_duration\n" +
//            "FROM songs \n" +
//            "INNER JOIN song_and_playlist ON song_and_playlist.s_id = songs.id\n" +
//            "INNER JOIN playlists ON playlists.id = song_and_playlist.p_id\n" +
//            "WHERE song_and_playlist.p_id = id\n" +
//            "GROUP BY playlists.name;", nativeQuery = true)
//    Iterable<Playlist> getPlaylistDurationAndName(@Param("id") Long id);

}
