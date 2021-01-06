package screens;

@SuppressWarnings("serial")
@Deprecated
public class PauseScreen {

	/*
	boolean used = false;
	boolean quiting = false;
	
	JButton resume = new JButton("Resume");
	JButton quit = new JButton("Quit");
	
	JLabel label = new JLabel("Press H to show hitboxes and press F to show FPS");
	
	JCheckBox fps = new JCheckBox("Show FPS");
	
	JFrame frame = new JFrame("Pause Screen");
	
	JPanel devPanel = new JPanel();
	
	JTextField command = new JTextField();
	
	GameLaunch game;
	Print p;
	
	
	public PauseScreen(GameLaunch game){
		this.game = game;
	}
	
	public void usePauseScreen(){
		if(!used){
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setResizable(false);
			frame.setLayout(new FlowLayout(FlowLayout.CENTER));
			
			frame.add(resume);
			frame.add(quit);
			frame.add(fps);
			frame.add(label);
			
			frame.add(command);
			
			frame.pack();
			frame.setLocationRelativeTo(Window.getGameScreen());
			frame.addWindowListener(this);
			frame.setVisible(true);
			resume.addActionListener(this);
			quit.addActionListener(this);
			fps.addActionListener(this);
		}
	}
	
	public void destroyPauseScreen(){
		if(used){
			frame.setVisible(false);
			frame.removeAll();
			frame.dispose();
			used = false;
		}
	}
	
	public final void quit(){
		quiting = true;
		frame.dispose();
		frame = null;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object code = e.getSource();
		if(code == resume){
			game.resume();
			frame.dispose();
		}
		if(code == fps){
			game.checkFPS();
		}
		if(code == quit){
			quiting = true;
			destroyPauseScreen();
			game.quit();
			game.reset();
			destroyPauseScreen();
		}
	}
	
	public JCheckBox getFps(){
		return fps;
	}
	
	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		if(!quiting){
			game.resume();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}
	*/
}
