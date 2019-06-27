package com.tasdiqhasan.junit.first;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MathTest {

  @Test(expected = IllegalArgumentException.class)
  public void testExceptionIsThrown() {
    Math tester = new Math();
    tester.multiply(1000, 5);
  }

  @Test
  public void testMultiply() {
    Math tester = new Math();
    assertEquals("10 x 5 must be 50", 50, tester.multiply(10, 5));
  }
}