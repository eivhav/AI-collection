package project2;


	
	
	import javax.swing.*;

import java.awt.*;

	@SuppressWarnings("serial")
	public class GUI extends JPanel {
	    private static final int TILE_SIZE = 128;
	    private static final int TILES_MARGIN = 16;
	    private static final String FONT_NAME = "Arial";
	    private static final Color[] TILE_COLORS = new Color[]{new Color(0x9e948a), new Color(0xeee4da), new Color(0xede0c8), new Color(0xf2b179), new Color(0xf59563), new Color(0xf67c5f),
	            new Color(0xf65e3b), new Color(0xedcf72), new Color(0xedcc61), new Color(0xedc850), new Color(0xedc53f), new Color(0xedc22e), new Color(0x3c3a32)};
	    private static final Color[] TEXT_COLORS = new Color[]{new Color(0x776E65), new Color(0xf9f6f2)};
	    private int[] board = null;


	    public static GUI getWindow() {
	        GUI gui = new GUI();
	        gui.setBackground(new Color(0x92877d));
	        JFrame game = new JFrame();
	        game.setTitle("2048");
	        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	        game.setResizable(false);
	        game.pack();
	        game.setSize(592 + game.getInsets().left + game.getInsets().right, 592 + game.getInsets().top + game.getInsets().bottom);

	        game.add(gui);
	        game.setLocationRelativeTo(null);
	        game.setVisible(true);
	        return gui;
	    }


	    public void drawBoard(int[] board) {
	        this.board = board;
	        repaint();
	    }


	    public void paint(Graphics g) {
	        super.paint(g);
	        for (int y = 0; y < 4; y++)
	            for (int x = 0; x < 4; x++)
	                drawTile(g, board[y*4 + x], x, y);
	    }


	    private void drawTile(Graphics g2, int value, int x, int y) {
	        Graphics2D g = ((Graphics2D) g2);
	        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);

	        int xOffset = offsetCoors(x);
	        int yOffset = offsetCoors(y);
	        g.setColor(TILE_COLORS[Math.min(12, value)]);
	        g.fillRect(xOffset, yOffset, TILE_SIZE, TILE_SIZE);
	        g.setColor(TEXT_COLORS[(value < 3) ? 0 : 1]);
	        final int size = value < 7 ? 36 : value < 10 ? 32 : 24;
	        final Font font = new Font(FONT_NAME, Font.BOLD, size);
	        g.setFont(font);

	        String s = String.valueOf(1<<value);
	        final FontMetrics fm = getFontMetrics(font);

	        final int w = fm.stringWidth(s);
	        final int h = -(int) fm.getLineMetrics(s, g).getBaselineOffsets()[2];

	        if (value != 0)
	            g.drawString(s, xOffset + (TILE_SIZE - w) / 2, yOffset + TILE_SIZE - (TILE_SIZE - h) / 2 - 2);
	    }


	    private static int offsetCoors(int arg) {
	        return arg * (TILES_MARGIN + TILE_SIZE) + TILES_MARGIN;
	    }
	}
	


