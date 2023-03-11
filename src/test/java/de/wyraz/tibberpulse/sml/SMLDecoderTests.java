package de.wyraz.tibberpulse.sml;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.io.IOException;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;

import de.wyraz.tibberpulse.sml.SMLDecoder;

public class SMLDecoderTests {

	@Test
	public void testSMLDecoderInvalidStartSequence() throws Exception {
		String payload="1c1b1b1b010101017605128e14cf620062007265000001017601010765425a4444330b090145425a0100091f14010163df26007605128e14d06200620072650000070177010b090145425a0100091f14017262016505e465007a77078181c78203ff010101010445425a0177070100000009ff010101010b090145425a0100091f140177070100010800ff6401018001621e52fb69000002a05939af070177070100010801ff0101621e52fb69000002a0531f2f070177070100010802ff0101621e52fb6900000000061a80000177070100020800ff6401018001621e52fb69000000000d7b8b460177070100100700ff0101621b52fe5500013c060177070100240700ff0101621b52fe55000058f00177070100380700ff0101621b52fe55000085f901770701004c0700ff0101621b52fe5500005d1d010101636c93007605128e14d162006200726500000201710163c682000000001b1b1b1b1a032f09";
		
		assertThatCode(()->{
			SMLDecoder.decode(Hex.decodeHex(payload));
		})
			.isInstanceOf(IOException.class)
			.hasMessage("Invalid SML payload");
	}
	@Test
	public void testSMLDecoderInvalidEndSequence() throws Exception {
		String payload="1b1b1b1b010101017605128e14cf620062007265000001017601010765425a4444330b090145425a0100091f14010163df26007605128e14d06200620072650000070177010b090145425a0100091f14017262016505e465007a77078181c78203ff010101010445425a0177070100000009ff010101010b090145425a0100091f140177070100010800ff6401018001621e52fb69000002a05939af070177070100010801ff0101621e52fb69000002a0531f2f070177070100010802ff0101621e52fb6900000000061a80000177070100020800ff6401018001621e52fb69000000000d7b8b460177070100100700ff0101621b52fe5500013c060177070100240700ff0101621b52fe55000058f00177070100380700ff0101621b52fe55000085f901770701004c0700ff0101621b52fe5500005d1d010101636c93007605128e14d162006200726500000201710163c682000000001c1b1b1b1a032f09";
		
		assertThatCode(()->{
			SMLDecoder.decode(Hex.decodeHex(payload));
		})
			.isInstanceOf(IOException.class)
			.hasMessage("Invalid SML payload");
	}
	@Test
	public void testSMLDecoderInvalidCRC() throws Exception {
		String payload="1b1b1b1b010101017605128e14cf620062007265000001017601010765425a4444330b090145425a0100091f14010163df26007605128e14d06200620072650000070177010b090145425a0100091f14017262016505e465007a77078181c78203ff010101010445425a0177070100000009ff010101010b090145425a0100091f140177070100010800ff6401018001621e52fb69000002a05939af070177070100010801ff0101621e52fb69000002a0531f2f070177070100010802ff0101621e52fb6900000000061a80000177070100020800ff6401018001621e52fb69000000000d7b8b460177070100100700ff0101621b52fe5500013c060177070100240700ff0101621b52fe55000058f00177070100380700ff0101621b52fe55000085f901770701004c0700ff0101621b52fe5500005d1d010101636c93007605128e14d162006200726500000201710163c682000000001b1b1b1b1a032f08";
		
		assertThatCode(()->{
			SMLDecoder.decode(Hex.decodeHex(payload));
		})
			.isInstanceOf(IOException.class)
			.hasMessage("Invalid SML payload: wrong crc");
	}
	
	/**
	 * Initial test with data from my own meter
	 */
	@Test
	public void testSMLDecoderEBZ() throws Exception {
		String payload="1b1b1b1b010101017605128e14cf620062007265000001017601010765425a4444330b090145425a0100091f14010163df26007605128e14d06200620072650000070177010b090145425a0100091f14017262016505e465007a77078181c78203ff010101010445425a0177070100000009ff010101010b090145425a0100091f140177070100010800ff6401018001621e52fb69000002a05939af070177070100010801ff0101621e52fb69000002a0531f2f070177070100010802ff0101621e52fb6900000000061a80000177070100020800ff6401018001621e52fb69000000000d7b8b460177070100100700ff0101621b52fe5500013c060177070100240700ff0101621b52fe55000058f00177070100380700ff0101621b52fe55000085f901770701004c0700ff0101621b52fe5500005d1d010101636c93007605128e14d162006200726500000201710163c682000000001b1b1b1b1a032f09";
				
		SMLMeterData data=SMLDecoder.decode(Hex.decodeHex(payload));
		
		assertThat(data).isNotNull();
		
		assertThat(data.getMeterId()).isEqualTo("1EBZ0100597780");
		
	}

}