package com.voca.eco.app.service;

import com.voca.eco.app.dto.NoteDTO;
import java.util.List;

public interface INoteService {

    List<NoteDTO> getNoteList(String userId, String date) throws Exception;
    List<NoteDTO> getNoteAll(String userId) throws Exception;
    List<NoteDTO> getNote(String userId) throws Exception;
    void insertWord(NoteDTO pDTO) throws Exception;

}
