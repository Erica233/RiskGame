package edu.duke.ece651.team3.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyNameTest {
  @Test
  public void test_getName() {
    assertEquals("team3", MyName.getName());
  }

}
