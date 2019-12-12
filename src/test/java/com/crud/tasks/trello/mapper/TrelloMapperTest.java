package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.facade.TrelloMapper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TrelloMapperTest {

    private TrelloMapper trelloMapper=new TrelloMapper();

    @Test
    public void mapToCardDto() throws Exception {
        //given
        TrelloCard card = new TrelloCard("card1","description1","pos1","id1");
        //when
        TrelloCardDto cardDto = trelloMapper.mapToCardDto(card);
        //then
        assertEquals(cardDto.getName(),"card1");
        assertEquals(cardDto.getDescription(),"description1");
        assertEquals(cardDto.getPos(),"pos1");
        assertEquals(cardDto.getListId(),"id1");
    }

    @Test
    public void mapToCard() throws Exception {
        //given
        TrelloCardDto cardDto = new TrelloCardDto("name1","description1","pos1","id1");
        //when
        TrelloCard card = trelloMapper.mapToCard(cardDto);
        //then
        assertEquals(card.getName(),"name1");
        assertEquals(card.getDescription(),"description1");
        assertEquals(card.getPos(),"pos1");
        assertEquals(card.getListId(),"id1");
    }

    @Test
    public void mapToList() throws Exception {
        //Given
        List<TrelloListDto> listDtos=new ArrayList<>();
        listDtos.add(new TrelloListDto("id1","name1",false));
        listDtos.add(new TrelloListDto("id2","name2",true));
        //When
        List<TrelloList> list = trelloMapper.mapToList(listDtos);
        //Then
        assertEquals(list.get(0).getId(),"id1");
        assertEquals(list.get(0).getName(), "name1");
        assertEquals(list.get(0).isClosed(),false);
        assertEquals(list.get(1).getId(),"id2");
        assertEquals(list.get(1).getName(), "name2");
        assertEquals(list.get(1).isClosed(),true);
    }

    @Test
    public void mapToListDto() throws Exception {
        //given
        List<TrelloList> list=new ArrayList<>();
        list.add(new TrelloList("id1","name1",false));
        list.add(new TrelloList("id2","name2",true));
        //when
        List<TrelloListDto> listDtos = trelloMapper.mapToListDto(list);
        //then
        assertEquals(listDtos.get(0).getId(),"id1");
        assertEquals(listDtos.get(0).getName(), "name1");
        assertEquals(listDtos.get(0).isClosed(),false);
        assertEquals(listDtos.get(1).getId(),"id2");
        assertEquals(listDtos.get(1).getName(), "name2");
        assertEquals(listDtos.get(1).isClosed(),true);
    }

    @Test
    public void mapToBoards() throws Exception {
        //given
        List<TrelloListDto> listDtos1=new ArrayList<>();
        listDtos1.add(new TrelloListDto("id1","name1",false));
        listDtos1.add(new TrelloListDto("id2","name2",true));
        List<TrelloListDto> listDtos2=new ArrayList<>();
        listDtos2.add(new TrelloListDto("id3","name3",false));

        List<TrelloBoardDto> boardDtos = new ArrayList<>();
        boardDtos.add(new TrelloBoardDto("id0", "name0", listDtos1));
        boardDtos.add(new TrelloBoardDto("id1", "name1", listDtos2));
        //when
        List<TrelloBoard> boards = trelloMapper.mapToBoards(boardDtos);
        //then
        assertEquals(boards.get(0).getId(),"id0");
        assertEquals(boards.get(1).getId(),"id1");
        assertEquals(boards.get(0).getName(),"name0");
        assertEquals(boards.get(1).getName(),"name1");
        assertEquals(boards.get(0).getLists().get(1).getId(), trelloMapper.mapToList(listDtos1).get(1).getId());
        assertEquals(boards.get(1).getLists().get(0).getName(), trelloMapper.mapToList(listDtos2).get(0).getName());
    }

    @Test
    public void mapToBoardsDto() throws Exception {
        //given
        List<TrelloList> list1=new ArrayList<>();
        list1.add(new TrelloList("id1","name1",false));
        List<TrelloList> list2=new ArrayList<>();
        list2.add(new TrelloList("id3","name3",false));

        List<TrelloBoard> board = new ArrayList<>();
        board.add(new TrelloBoard("id0", "name0", list1));
        board.add(new TrelloBoard("id1", "name1", list2));
        //when
        List<TrelloBoardDto> boardDtos = trelloMapper.mapToBoardsDto(board);
        //then
        assertEquals(boardDtos.get(0).getId(),"id0");
        assertEquals(boardDtos.get(1).getId(),"id1");
        assertEquals(boardDtos.get(0).getName(),"name0");
        assertEquals(boardDtos.get(1).getName(),"name1");
        assertEquals(boardDtos.get(0).getLists().get(0).getId(), "id1");
        assertEquals(boardDtos.get(1).getLists().get(0).getName(), "name3");
        assertEquals(boardDtos.get(1).getLists().get(0).isClosed(), false);
    }
}
