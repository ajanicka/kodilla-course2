package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloValidatorTest {

    @InjectMocks
    TrelloValidator trelloValidator;

    @Test
    public void validateTrelloBoards() {
        TrelloList trelloList = new TrelloList("id777", "name777", true);
        List<TrelloList> trelloListList = new ArrayList<>();
        trelloListList.add(trelloList);
        TrelloBoard trelloBoard = new TrelloBoard("id1", "test", trelloListList);
        List<TrelloBoard> trelloBoardList = new ArrayList<>();
        trelloBoardList.add(trelloBoard);

        assertEquals(0, trelloValidator.validateTrelloBoards(trelloBoardList).size());
    }
}