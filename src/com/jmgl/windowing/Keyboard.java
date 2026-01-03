package com.jmgl.windowing;

import org.lwjgl.glfw.GLFW;

public final class Keyboard
{
	// Key consts
	public static final int KEYS_ALL = GLFW.GLFW_KEY_LAST + 1;
	
	// Keyboard status
	private static boolean exists = false;
	private static boolean[] pressed = new boolean[KEYS_ALL];
	
	private static void init()
	{
		if (exists)
		{
			System.err.println("Keyboard has already been initialized!");
			return;
		}
		
		Display.setKeyInputListener((key, scanCode, action, mods) -> 
		{
			if (key == GLFW.GLFW_KEY_UNKNOWN) return;
			
			if (action == GLFW.GLFW_PRESS)
			{
				pressed[key] = true;
			}
			else if (action == GLFW.GLFW_RELEASE)
			{
				pressed[key] = false;
			}
		});
		
		exists = true;
	}
	
	private static void checkInit()
	{
		if (!exists) init();
	}
	
	public static boolean isKeyDown(int key)
	{
		checkInit();
		return pressed[key];
	}
	
	public static float getKeyDown(int key)
	{
		checkInit();
		return pressed[key] ? 1.0f : 0.0f;
	}
	
	public static float getKeyAxis(int negKey, int posKey)
	{
		checkInit();
		if (pressed[negKey]) return -1.0f;
		else if (pressed[posKey]) return 1.0f;
		else return 0.0f;
	}
}
