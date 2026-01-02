package com.jmgl.windowing;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * Base class for Windowing by leveraging GLFW.
 * Note: Default OpenGL version is 3.3.0 Core profile.
 */
public final class Display
{
	// Display consts
	public static final int PROFILE_CORE = GLFW.GLFW_OPENGL_CORE_PROFILE;
	public static final int PROFILE_ANY = GLFW.GLFW_OPENGL_ANY_PROFILE;
	public static final int PROFILE_COMPAT = GLFW.GLFW_OPENGL_COMPAT_PROFILE;
	
	// Display configs
	private static String title;
	private static int width, height;
	private static int depth = 24;
	private static int stencil = 4;
	private static boolean resizable = true;
	private static boolean vsync = false;
	private static boolean forwardCompat = false;
	private static boolean fullscreen = false;
	private static int glMajor = 3, glMinor = 3;
	private static int glProfile = PROFILE_CORE;
	
	private static int minWidth = 640;
	private static int minHeight = 460;
	private static int maxWidth = GLFW.GLFW_DONT_CARE;
	private static int maxHeight = GLFW.GLFW_DONT_CARE;
	
	// Display Callbacks
	private static DisplayResize displayResizeListener;
	
	// GLFW Window
	private static long windowPtr;
	private static GLFWVidMode primaryMode;
	private static boolean exists = false;
	
	/**
	 * Sets specified title for the window.
	 * @param title
	 */
	public static void setTitle(String title)
	{
		Display.title = title;
		if (exists)
		{
			GLFW.glfwSetWindowTitle(windowPtr, title);
		}
	}
	
	/**
	 * Sets specified size for the window.
	 * @param width
	 * @param height
	 */
	public static void setSize(int width, int height)
	{
		Display.width = width;
		Display.height = height;
		
		if (exists)
    	{
    		GLFW.glfwSetWindowSize(windowPtr, width, height);
    	}
	}
	
	public static void setMinimumSize(int width, int height)
	{
		minWidth = width;
		minHeight = height;
		
		if (exists)
		{
			GLFW.glfwSetWindowSizeLimits(windowPtr, minWidth, minHeight, maxWidth, maxHeight);
		}
	}
	
	public static void setMaximumSize(int width, int height)
	{
		maxWidth = width;
		maxHeight = height;
		
		if (exists)
		{
			GLFW.glfwSetWindowSizeLimits(windowPtr, minWidth, minHeight, maxWidth, maxHeight);
		}
	}
	
	/**
	 * Allows setting VSync to true or false.
	 * @param vsync
	 */
	public static void enableVSync(boolean vsync)
	{
		Display.vsync = vsync;
	}
	
	/**
	 * Allowing window to be resizable.
	 * @param resizable
	 */
	public static void setResizable(boolean resizable)
	{
		Display.resizable = resizable;
	}
	
	/**
	 * Allowing for immediate GL calls while using modern GL.
	 * @param forwardCompat
	 */
	public static void setForwardCompat(boolean forwardCompat)
	{
		Display.forwardCompat = forwardCompat;
	}
	
	/**
	 * Allowing fullscreen on the window.
	 * @param fullscreen
	 */
	public static void setFullscreen(boolean fullscreen)
	{
		Display.fullscreen = fullscreen;
	}
	
	/**
	 * Sets the depth. Default is 24
	 * @param depth
	 */
	public static void setDepth(int depth)
	{
		Display.depth = depth;
	}
	
	/**
	 * Sets the stencil. Default is 4
	 * @param stencil
	 */
	public static void setStencil(int stencil)
	{
		Display.stencil = stencil;
	}
	
	/**
	 * Allows for simple setup for the OnFramebufferResizeCallback via interface.
	 * @param listener your specified listener!
	 */
	public static void setResizeListener(DisplayResize listener)
	{
		displayResizeListener = listener;
		System.out.println("Display: OnDisplayResize Listener has been hooked up!");
	}
	
	public static void setGLProfile(int major, int minor, int profile)
	{
		glMajor = major;
		glMinor = minor;
		glProfile = profile;
	}
	
	/**
	 * Call this once after you set up your window configs to run the window.
	 * @param adaptSize Whether you want to adapt the window size on fullscreen to primary monitor size or for your desired window size. Useful for retro.
	 */
	public static void create(boolean adaptSize)
	{
		if (exists)
		{
			System.err.println("Display is already created!");
		}
		
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!GLFW.glfwInit())
		{
			throw new IllegalStateException("Unable to initialize GLFW!");
		}
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_DEPTH_BITS, depth);
		GLFW.glfwWindowHint(GLFW.GLFW_STENCIL_BITS, stencil);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, resizable ? GLFW.GLFW_TRUE : GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, glMajor);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, glMinor);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, glProfile);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, forwardCompat ? GL11.GL_TRUE : GL11.GL_FALSE);
        
        primaryMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        
        // Calculations whether to adapt size or let it be.
        int w = (fullscreen && adaptSize) ? primaryMode.width() : width;
        int h = (fullscreen && adaptSize) ? primaryMode.height() : height;
        width = w;
        height = h;
        
        GLFW.glfwWindowHint(GLFW.GLFW_RED_BITS, primaryMode.redBits());
        GLFW.glfwWindowHint(GLFW.GLFW_GREEN_BITS, primaryMode.greenBits());
        GLFW.glfwWindowHint(GLFW.GLFW_BLUE_BITS, primaryMode.blueBits());
        GLFW.glfwWindowHint(GLFW.GLFW_REFRESH_RATE, primaryMode.refreshRate());
        
        windowPtr = GLFW.glfwCreateWindow(width, height, title, fullscreen ? GLFW.glfwGetPrimaryMonitor() : NULL, NULL);
        if (windowPtr == NULL)
        {
            throw new RuntimeException("Failed to create GLFW Window!");
        }
        
        GLFW.glfwSetFramebufferSizeCallback(windowPtr, (window, width, height) ->
        {
            Display.width = width;
            Display.height = height;
            
            if (displayResizeListener != null)
            	displayResizeListener.OnDisplayResize(width, height);
        });
        
        GLFW.glfwSetKeyCallback(windowPtr, (window, key, scanCode, action, mods) ->
        {
            if (key == GLFW.GLFW_KEY_ESCAPE && action == GLFW.GLFW_PRESS)
            {
                close();
            }
        });
        
        GLFW.glfwMakeContextCurrent(windowPtr);
        GLFW.glfwSwapInterval(vsync ? 1 : 0);
        GLFW.glfwShowWindow(windowPtr);
        
        GL.createCapabilities();
        exists = true;
        System.out.println("Display has been created & run!");
	}
	
	public static void close()
	{
		if (!exists) return;
		
		exists = false;
		GLFW.glfwSetWindowShouldClose(windowPtr, true);
	}
	
	public static void free()
	{
		if (exists) return;
		
		GLFW.glfwSetErrorCallback(null);
		GLFW.glfwSetKeyCallback(windowPtr, null);
		
		GLFW.glfwDestroyWindow(windowPtr);
        GLFW.glfwTerminate();
	}
	
	public static void checkExists()
	{
		if (!exists)
		{
			throw new RuntimeException("Display does not exist anymore!");
		}
	}
	
	public static void pollEvents()
	{
		checkExists();
		GLFW.glfwPollEvents();
	}
	
	public static void swapBuffers()
	{
		checkExists();
		GLFW.glfwSwapBuffers(windowPtr);
	}
	
	public static void update()
	{
		swapBuffers();
		pollEvents();
	}
	
	public static void destroy()
	{
		close();
		free();
	}
	
	// ---> GETTERS <---
	
	public static boolean isCloseRequested()
	{
		return GLFW.glfwWindowShouldClose(windowPtr);
	}
	
	public static boolean exists()
    {
    	return exists;
    }
	
	public static int getWidth()
	{
		return width;
	}
	
	public static int getHeight()
	{
		return height;
	}
	
	public static float getAspectRatio()
	{
		return (float)width / (float)height;
	}
	
	public static GLFWVidMode getPrimaryMode()
	{
		checkExists();
		return primaryMode;
	}
}
