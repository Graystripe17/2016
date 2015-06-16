package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor{
	SpriteBatch batch;
	private long screenWidth, screenHeight;
	private final int maxMultitouch = 2;
	private final int maxMagnitude = 200;
	private final int impedence = 50;
	private final int wheelSensitivity = 10;
	private final int aboveWheelDisplacement = 120;
	private final int spacing = 600;
	private final float rotateSpeed = 0.1f;
	private String message = "";
	private String message2 = "";
	private Texture blueImage, redImage, greenImage, cyanImage, orangeImage, purpleImage;
	private Sprite blueSprite, redSprite, greenSprite, cyanSprite, orangeSprite, purpleSprite;
	private Texture plusImage;
	private Sprite plusSprite;
	private Texture flowerImage;
	private Sprite flowerSprite;
	private Texture SquarePyramidImage;
	private Sprite SquarePyramidSprite;
	private Texture dolphinImage;
	private Sprite dolphinSprite;
	private Rectangle host;
	private Array<Rectangle> pellets;
	private long lastPelletTime;
	private double firstAngle;
	private int leftPoint = 0;
	private int pods = 4;
	private BitmapFont debuggerText;
	// Method onTouchDrag
	double DeltaX = 0, DeltaY = 0, magnitude = 0, angle = 0;

	private Music BobMarley;

	class TouchInfo {
		public float touchX = 0;
		public float touchY = 0;
		public boolean touched = false;
		public boolean onLeft = false;
	}
	static class rightAnchor {
		public static float posX = 0;
		public static float posY = 0;
		public static boolean active = false;
	}
	static class leftAnchor {
		public static float posX = 0;
		public static float posY = 0;
		public static boolean active = false;
	}

	private Map<Integer, TouchInfo> touches = new HashMap<Integer, TouchInfo>();




	//Beginning of Method
	@Override
	public void create () {
		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		batch = new SpriteBatch();
		debuggerText = new BitmapFont();
		debuggerText.setColor(Color.RED);
		blueImage = new Texture(Gdx.files.internal("Blue.png"));
		redImage = new Texture(Gdx.files.internal("Red.png"));
		greenImage = new Texture(Gdx.files.internal("Green.png"));
		purpleImage = new Texture(Gdx.files.internal("Purple.png"));
		orangeImage = new Texture(Gdx.files.internal("Orange.png"));
		cyanImage = new Texture(Gdx.files.internal("Cyan.png"));
		SquarePyramidImage = new Texture(Gdx.files.internal("SquarePyramid.png"));
		flowerImage = new Texture(Gdx.files.internal("Flower.png"));
		dolphinImage = new Texture(Gdx.files.internal("dolphin.jpg"));
		plusImage = new Texture(Gdx.files.internal("Plus.png"));

		blueSprite = new Sprite(blueImage);
		redSprite = new Sprite(redImage);
		greenSprite = new Sprite(greenImage);
		purpleSprite = new Sprite(purpleImage);
		orangeSprite = new Sprite(orangeImage);
		cyanSprite = new Sprite(cyanImage);
		SquarePyramidSprite = new Sprite(SquarePyramidImage);
		flowerSprite = new Sprite(flowerImage);
		dolphinSprite = new Sprite(dolphinImage);
		plusSprite = new Sprite(plusImage);

		host = new Rectangle(screenWidth/2-50, screenHeight/2-50, 100, 100);
		pellets = new Array<Rectangle>();

		Gdx.input.setInputProcessor(this);

		//Maximum of two touches
		for(int i = 0; i < maxMultitouch; i++) {
			touches.put(i, new TouchInfo());
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		batch.begin();
		batch.draw(blueImage, 0, 0);
		batch.draw(redImage, 0, 50);
		dolphinSprite.draw(batch);
				cyanSprite.setX(dolphinSprite.getX() + spacing * (float) Math.cos(rotateSpeed * greenSprite.getRotation()));
				cyanSprite.setY(dolphinSprite.getY() + spacing * (float) Math.sin(rotateSpeed * greenSprite.getRotation()));
				cyanSprite.draw(batch);
				blueSprite.setX(dolphinSprite.getX() + spacing * (float) (Math.cos(rotateSpeed * greenSprite.getRotation() + 1 * 2*Math.PI/pods)));
				blueSprite.setY(dolphinSprite.getY() + spacing * (float) (Math.sin(rotateSpeed * greenSprite.getRotation() + 1 * 2*Math.PI/pods)));
				blueSprite.draw(batch);
				redSprite.setX(dolphinSprite.getX() + spacing * (float) (Math.cos(rotateSpeed * greenSprite.getRotation() + 2 * 2*Math.PI/pods)));
				redSprite.setY(dolphinSprite.getY() + spacing * (float) (Math.sin(rotateSpeed * greenSprite.getRotation() + 2 * 2*Math.PI/pods)));
				redSprite.draw(batch);
				orangeSprite.setX(dolphinSprite.getX() + spacing * (float) (Math.cos(rotateSpeed * greenSprite.getRotation() + 3 * 2*Math.PI/pods)));
				orangeSprite.setY(dolphinSprite.getY() + spacing * (float) (Math.sin(rotateSpeed * greenSprite.getRotation() + 3 * 2*Math.PI/pods)));
				orangeSprite.draw(batch);
			if(rightAnchor.active) {
				batch.draw(flowerImage, rightAnchor.posX - flowerImage.getWidth()/2, screenHeight - rightAnchor.posY - flowerImage.getHeight()/2 - aboveWheelDisplacement);
			}
			if(leftAnchor.active) {
				batch.draw(SquarePyramidImage, leftAnchor.posX - SquarePyramidImage.getWidth() / 2, screenHeight - leftAnchor.posY - SquarePyramidImage.getHeight() / 2);
				// May not be considered good design
				moveDolphin();
			}
			/*
				for(Rectangle raindrop: rain) {
					batch.draw(moneyImage, raindrop.x, raindrop.y);
				}
			*/
			message = "firstAngle" + firstAngle
					+ "\nangle" + angle
					+ "\nDeltaX" + DeltaX
					+ "\nDeltaY" + DeltaY
					+ "\nTouchesP1" + touches.get(0).touchX
					+ "\nTouchesP2" + touches.get(1).touchY
					+ "\nRightAncX" + rightAnchor.posX
					+ "\nRightAncY" + rightAnchor.posY
					+ "\nMagnitude" + magnitude;
			debuggerText.draw(batch, message, 200, 200);
			debuggerText.draw(batch, message2, 600, 800);
		batch.end();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX() ,Gdx.input.getY(), 0);
		}

		if(TimeUtils.nanoTime() - lastPelletTime > 1E6) {

		}
	}

	@Override
	public void dispose() {
		redImage.dispose();
		blueImage.dispose();
		greenImage.dispose();
		BobMarley.dispose();
		debuggerText.dispose();
		batch.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(pointer < maxMultitouch) {
			// Set position
			touches.get(pointer).touchX = screenX;
			touches.get(pointer).touchY = screenY;
			touches.get(pointer).touched = true;
			// There can only be one anchor per side.
			// Is touch anchored on left?
			if (touches.get(pointer).touchX < screenWidth/2) {
				touches.get(pointer).onLeft = true;
				leftAnchor.posX = screenX;
				leftAnchor.posY = screenY;
				leftAnchor.active = true;
			} else {
				touches.get(pointer).onLeft = false;
				rightAnchor.posX = screenX;
				rightAnchor.posY = screenY;
				rightAnchor.active = true;
				firstAngle = Math.atan2(touches.get(pointer).touchY - rightAnchor.posY + aboveWheelDisplacement, touches.get(pointer).touchX - rightAnchor.posX);
			}
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(pointer < maxMultitouch) {
			touches.get(pointer).touchX = 0;
			touches.get(pointer).touchY = 0;
			touches.get(pointer).touched = false;
			// Is touch anchored on left?
			if (touches.get(pointer).onLeft) {
				leftAnchor.posX = 0;
				leftAnchor.posY = 0;
				leftAnchor.active = false;
			} else {
				rightAnchor.posX = 0;
				rightAnchor.posY = 0;
				rightAnchor.active = false;
			}
		}
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if(pointer < maxMultitouch) {
			// Constantly update touches
			touches.get(pointer).touchX = screenX;
			touches.get(pointer).touchY = screenY;
			touches.get(pointer).touched = true;
			if (touches.get(pointer).onLeft) {
				// Which point is one on the left?
				leftPoint = pointer;
			} else {
				// This point is on right
				DeltaX = touches.get(pointer).touchX - rightAnchor.posX;
				DeltaY = touches.get(pointer).touchY - rightAnchor.posY - aboveWheelDisplacement;
				angle = Math.atan2(DeltaY, DeltaX);
				// Convert range [-pi, pi] to [0, 2pi]
				angle += Math.PI;
				// At some point, firstAngle is vastly different from angle
				// Temporary workaround: ask SO in the future
				if(Math.abs(firstAngle - angle) < 5) {
					greenSprite.rotate((float)(firstAngle - angle) * wheelSensitivity);
				}
				// Update to angle@T1
				firstAngle = angle;
			}
			// Calculations for onLeft
			// Plan: Calculate |R|. If |R| > maxRange, just use angle & power 1
			// Else use |R| and angle
			// angle@T0 - angle@T1, rotate sprite that amount.

		}
		return true;
	}

	public void moveDolphin() {
		DeltaX = touches.get(leftPoint).touchX - leftAnchor.posX;
		DeltaY = touches.get(leftPoint).touchY - leftAnchor.posY;
		// If the drag magnitude too great, cut at max
		magnitude = Math.hypot(DeltaX, DeltaY);
		magnitude = (magnitude > maxMagnitude) ? maxMagnitude : magnitude;
		angle = Math.atan2(DeltaY, DeltaX);
		dolphinSprite.setX((float)(magnitude * Math.cos(angle))/impedence + dolphinSprite.getX());
		dolphinSprite.setY((float)(magnitude * (-1) * Math.sin(angle))/impedence + dolphinSprite.getY());
		message2 = dolphinSprite.getY() + "\n" + dolphinSprite.getX();
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}
	@Override
	public boolean keyUp(int keycode) {
		return false;
	}
	@Override
	public boolean keyTyped(char character) {
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}















