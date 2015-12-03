package test;

import static org.junit.Assert.*;

import org.junit.Test;

import source.Sound;

public class SoundTest {

	@Test
	public void testProduceFeedback() throws Exception {
		Sound sound = new Sound();
		assertEquals(sound.produceFeedback("left"), 1);
		assertEquals(sound.produceFeedback("right"), 2);
		assertEquals(sound.produceFeedback("omnidirectional"), 3);
		assertEquals(sound.produceFeedback("nwejkfenfwe"), -1);
	}
}
