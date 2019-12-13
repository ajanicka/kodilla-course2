package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TrelloController.class)
public class TrelloControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrelloFacade trelloFacade;

    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception {
//        given
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);
//        when & then
        mockMvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))    //or isOk()
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTrelloBoards() throws Exception {
//        given
        List<TrelloListDto> listDtos = new ArrayList<>();
        listDtos.add(new TrelloListDto("1", "the_list", false));

        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "test task", listDtos));

        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);
//        when and then
        mockMvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("test task")))
                .andExpect(jsonPath("$[0].lists", hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id", is("1")))
                .andExpect(jsonPath("$[0].lists[0].name", is("the_list")));
    }

    @Test
    public void shouldCreateTrelloCard() throws Exception {
//        given
        TrelloCardDto trelloCardDto = new TrelloCardDto("test", "test description",
                "top", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("323", "test", "http://test.com");

        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);

        when(trelloFacade.createCard(ArgumentMatchers.any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);

//        when & then
        mockMvc.perform(post("/v1/trello/createTrelloCard")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is("323")))
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.shortUrl", is("http://test.com")));
    }
}