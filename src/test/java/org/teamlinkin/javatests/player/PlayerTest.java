package org.teamlinkin.javatests.player;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void looses_when_dice_number_is_too_low() {
        Dice dice = Mockito.mock(Dice.class);
        Mockito.when(dice.roll()).thenReturn(2);
        Player player = new Player(dice,2);
        assertFalse(player.play());
        //Se espera que pierda
    }
    @Test
    public void looses_when_dice_number_is_bigger() {
        Dice dice = Mockito.mock(Dice.class);
        Mockito.when(dice.roll()).thenReturn(4);
        Player player = new Player(dice,3);
        assertEquals(true,player.play());
        //Se espera que gane
    }


}