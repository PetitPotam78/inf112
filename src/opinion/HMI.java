package opinion;


import hmi.InputJPanel;
import hmi.PasswordJPanel;
import hmi.TextJScrollPane;

import javax.swing.*;




import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * The <i>HMI</i> class allows the interactive use of a <i>SocialNetwork</i>.
 * @author  B. Prou
 * @author E. Cousin (English translation)
 */
public class HMI {





	/**
	 * @uml.property  name="sn"
	 * @uml.associationEnd  
	 */
	private ISocialNetwork sn = null;



	private int windowWidth = 600;

	private String memberLogin = "";
	private String memberPassword = "";
	private String memberProfile = "";

	String bookTitle;
	String bookKind;
	String bookAuthor;
	String bookNbPages;

	String filmTitle;
	String filmKind;
	String filmDirector;
	String filmScenarist;
	String filmDuration;

	String comment;
	String mark;

	private JFrame interactionWindow;
	private JMenuBar menuBar;
	private JMenu memberMenu;
	private JMenu visitorMenu;
	
	private JFrame outputWindow;
	/**
	 * @uml.property  name="outputWindowJScrollPane"
	 * @uml.associationEnd  
	 */
	private TextJScrollPane outputWindowJScrollPane;
	
	private JFrame itemOutputWindow;
	/**
	 * @uml.property  name="itemOutputJScrollPane"
	 * @uml.associationEnd  
	 */
	private TextJScrollPane itemOutputJScrollPane;


	public HMI() {
		// Outputs for the user are given in french
		
		JMenuItem jMenuItem;
		interactionWindow = new JFrame("IHM de SocialNetwork : ");
		interactionWindow.setSize(windowWidth + 100, 100);
		interactionWindow.setVisible(true);
		//Font f = fenetreInteraction.getFont().deriveFont(Font.ITALIC + Font.BOLD);
		interactionWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar = new JMenuBar();
		outputWindow = null;

		
		memberMenu = new JMenu("membre"); // member
		jMenuItem = new JMenuItem("afficher le réseau social"); // print content of the social network
		jMenuItem.addActionListener(new ShowNetwork());
		memberMenu.add(jMenuItem);
		jMenuItem = new JMenuItem("consulter un item"); // consult an item
		jMenuItem.addActionListener(new ConsultItem());
		memberMenu.add(jMenuItem);
		jMenuItem = new JMenuItem("ajouter un profil"); // add a member
		jMenuItem.addActionListener(new AddMember());
		memberMenu.add(jMenuItem);
		jMenuItem = new JMenuItem("ajouter un item livre"); // add a book
		jMenuItem.addActionListener(new AddItemBook());
		memberMenu.add(jMenuItem);
		jMenuItem = new JMenuItem("ajouter un item film"); // add a film
		jMenuItem.addActionListener(new AddItemFilm());
		memberMenu.add(jMenuItem);
		jMenuItem = new JMenuItem("donner un avis sur un item livre"); // rate an item
		jMenuItem.addActionListener(new ReviewItem("livre")); // book
		memberMenu.add(jMenuItem);
		jMenuItem = new JMenuItem("donner un avis sur un item film"); // rate a film
		jMenuItem.addActionListener(new ReviewItem("film")); // film
		memberMenu.add(jMenuItem);


		visitorMenu = new JMenu("visiteur"); // anonymous user
		jMenuItem = new JMenuItem("consulter un item"); // consult an item
		jMenuItem.addActionListener(new ConsultItem());
		visitorMenu.add(jMenuItem);
		jMenuItem = new JMenuItem("ajouter un profil"); // add a member
		jMenuItem.addActionListener(new AddMember());
		visitorMenu.add(jMenuItem);

		menuBar.add(memberMenu);
		menuBar.add(visitorMenu);

		interactionWindow.setJMenuBar(menuBar);
		interactionWindow.setVisible(true);
	}




	/**
	 * Setter of the property <tt>topic</tt>
	 * @param topic  The topic to set.
	 * @uml.property  name="topic"
	 */
	public void setTopic(ISocialNetwork topic) {
		this.sn = topic;
	}



	private class ShowNetwork  implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (outputWindow == null) {
				outputWindow = new JFrame();
				outputWindow.setLocation(60,60);
				outputWindowJScrollPane = new TextJScrollPane(" Social Network ",  "", false, windowWidth);
				outputWindowJScrollPane.setText(sn.toString());
				outputWindow.getContentPane().removeAll();
				outputWindow.repaint();
				outputWindow.setSize(windowWidth + 100, 350);
				outputWindow.getContentPane().add(outputWindowJScrollPane);
				outputWindowJScrollPane.setVisible(true);
				outputWindow.setVisible(true);
				outputWindow.repaint();
			} else {
				outputWindowJScrollPane.setText(sn.toString());
				outputWindow.setVisible(true);
				outputWindow.repaint();				
			}
		}

	}



	/**
	 * @author  prou
	 */
	private class ConsultItem implements ActionListener {
		/**
		 * @uml.property  name="jPanelNameItem"
		 * @uml.associationEnd  
		 */
		InputJPanel itemTitleJPanel;
		public void actionPerformed(ActionEvent e) {
			JPanel consultItem = new JPanel();
			consultItem.setLayout(new GridLayout(2,1, 4, 4));
			itemTitleJPanel = new InputJPanel(" nom de l'item ? ", "", windowWidth); // item's title
			consultItem.add(itemTitleJPanel);
			JButton confirm = new JButton("valider la demande consultation "); // confirm action
			confirm.setPreferredSize(new Dimension(windowWidth-20, 25));
			confirm.addActionListener(new ConsultItemAction());
			consultItem.add(confirm);
			consultItem.setVisible(false);
			interactionWindow.getContentPane().removeAll();
			interactionWindow.repaint();
			interactionWindow.setSize(windowWidth + 100, 120);
			interactionWindow.getContentPane().add(consultItem);
			consultItem.setVisible(true);			
			interactionWindow.repaint();
		}
		class ConsultItemAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					LinkedList <String> items = sn.consultItems(itemTitleJPanel.getEntree());
					String s = "\n";
					for (String item : items) {
						s += item + "\n";
					}
					if (itemOutputWindow == null) {
						itemOutputWindow = new JFrame();
						itemOutputWindow.setLocation(30,30);
						itemOutputJScrollPane = new TextJScrollPane(" items ",  s, false, windowWidth);
						itemOutputWindow.getContentPane().removeAll();
						itemOutputWindow.repaint();
						itemOutputWindow.setSize(windowWidth + 100, 350);
						itemOutputWindow.getContentPane().add(itemOutputJScrollPane);
						itemOutputJScrollPane.setVisible(true);
						itemOutputWindow.setVisible(true);
						itemOutputWindow.repaint();
					} else {
						itemOutputJScrollPane.setText(s);
						itemOutputWindow.setVisible(true);
						itemOutputWindow.repaint();				
					}

				}
				catch (Exception exception) {
					JOptionPane.showMessageDialog(interactionWindow, "Exception dans consultItems :   "  + exception);
				}
			}
		}

	}



	/**
	 * @author  prou
	 */
	private class AddMember implements ActionListener {
		/**
		 * @uml.property  name="pseudoJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel pseudoJPanel;
		/**
		 * @uml.property  name="passwordJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel passwordJPanel;
		/**
		 * @uml.property  name="jScrollPaneProfil"
		 * @uml.associationEnd  
		 */
		TextJScrollPane profileJScrollPane;
		public void actionPerformed(ActionEvent e) {
			JPanel addMemberJPanel = new JPanel();
			profileJScrollPane = new TextJScrollPane(" profil du membre ? ",  memberProfile, true, windowWidth); // member's profile ?
			addMemberJPanel.add(profileJScrollPane);
			JPanel loginAndPwdJPanel = new JPanel();
			loginAndPwdJPanel.setLayout(new GridLayout(3,1, 4, 4));
			pseudoJPanel = new InputJPanel(" pseudo ? ", memberLogin, windowWidth); // member's login
			loginAndPwdJPanel.add(pseudoJPanel);
			passwordJPanel = new InputJPanel(" mot de passe ? ", memberPassword, windowWidth); // member's password ?
			loginAndPwdJPanel.add(passwordJPanel);
			JButton confirm = new JButton("valider son inscription "); // confirm member's creation
			confirm.setPreferredSize(new Dimension(windowWidth-20, 25));
			confirm.addActionListener(new AddMemberAction());
			loginAndPwdJPanel.add(confirm);
			loginAndPwdJPanel.setVisible(true);
			addMemberJPanel.add(loginAndPwdJPanel);
			addMemberJPanel.setVisible(false);
			interactionWindow.getContentPane().removeAll();
			interactionWindow.repaint();
			interactionWindow.setSize(windowWidth + 100, 410);
			interactionWindow.getContentPane().add(addMemberJPanel);
			addMemberJPanel.setVisible(true);			
			interactionWindow.repaint();
		}

		class AddMemberAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					memberLogin = pseudoJPanel.getEntree();
					memberPassword = passwordJPanel.getEntree();
					memberProfile = profileJScrollPane.getText();
					sn.addMember(memberLogin, memberPassword, memberProfile);
				}
				catch (Exception exception) {
					JOptionPane.showMessageDialog(interactionWindow, "Exception dans addMember :   "  + exception);
				}
			}
		}
	}




	/**
	 * @author  prou
	 */
	private class AddItemBook implements ActionListener {
		/**
		 * @uml.property  name="loginJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel loginJPanel;
		/**
		 * @uml.property  name="passwordJPanel"
		 * @uml.associationEnd  
		 */
		PasswordJPanel passwordJPanel;
		/**
		 * @uml.property  name="titleJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel titleJPanel;
		/**
		 * @uml.property  name="kindJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel kindJPanel;
		/**
		 * @uml.property  name="authorJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel authorJPanel;
		/**
		 * @uml.property  name="nbPagesJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel nbPagesJPanel;
		
		public void actionPerformed(ActionEvent e) {
			JPanel addBookJP = new JPanel();
			addBookJP.setLayout(new GridLayout(7,1, 4, 4));
			loginJPanel = new InputJPanel(" pseudo membre ? ", memberLogin, windowWidth); // member's login ?
			addBookJP.add(loginJPanel);
			passwordJPanel = new PasswordJPanel(" password membre ? ", memberPassword, windowWidth); // member's password ?
			addBookJP.add(passwordJPanel);
			titleJPanel = new InputJPanel(" titre ? ", bookTitle, windowWidth); // book's title ?
			addBookJP.add(titleJPanel);
			kindJPanel = new InputJPanel(" genre ? ", bookKind, windowWidth); // book's kind ?
			addBookJP.add(kindJPanel);
			authorJPanel = new InputJPanel(" auteur ? ", bookAuthor, windowWidth); // book's author ?
			addBookJP.add(authorJPanel);
			nbPagesJPanel = new InputJPanel(" nb pages ? ", bookNbPages, windowWidth); // book's nb of pages
			addBookJP.add(nbPagesJPanel);
			JButton confirm = new JButton("Valider ajouter item livre");  // confirm add a book
			confirm.setPreferredSize(new Dimension(windowWidth-20, 25));
			confirm.addActionListener(new AddItemBookAction());
			addBookJP.add(confirm);
			addBookJP.setVisible(false);
			interactionWindow.getContentPane().removeAll();
			interactionWindow.repaint();
			interactionWindow.setSize(windowWidth + 100, 320);
			interactionWindow.getContentPane().add(addBookJP);
			addBookJP.setVisible(true);			
			interactionWindow.setVisible(true);			
			interactionWindow.repaint();
		}

		class AddItemBookAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					memberLogin = loginJPanel.getEntree(); 
					memberPassword = passwordJPanel.getPassword(); 
					bookTitle = titleJPanel.getEntree(); 
					bookKind = kindJPanel.getEntree(); 
					bookAuthor = authorJPanel.getEntree(); 
					bookNbPages = nbPagesJPanel.getEntree(); 
					sn.addItemBook(memberLogin, memberPassword, bookTitle, bookKind, bookAuthor, new Integer(bookNbPages));
				}
				catch (Exception exception) {
					JOptionPane.showMessageDialog(interactionWindow, "Exception dans addItemBook :   "  + exception);
				}
			}
		}
	}


	
	private class AddItemFilm implements ActionListener {
		/**
		 * @uml.property  name="loginJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel loginJPanel;
		/**
		 * @uml.property  name="passwordJPanel"
		 * @uml.associationEnd  
		 */
		PasswordJPanel passwordJPanel;
		/**
		 * @uml.property  name="titleJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel titleJPanel;
		/**
		 * @uml.property  name="kindJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel kindJPanel;
		/**
		 * @uml.property  name="directorJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel directorJPanel;
		/**
		 * @uml.property  name="scenaristJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel scenaristJPanel;
		/**
		 * @uml.property  name="nbPagesJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel nbPagesJPanel;
		
		public void actionPerformed(ActionEvent e) {
			JPanel addFilmJP = new JPanel();
			addFilmJP.setLayout(new GridLayout(7,1, 4, 4));
			loginJPanel = new InputJPanel(" pseudo membre ? ", memberLogin, windowWidth);
			addFilmJP.add(loginJPanel);
			passwordJPanel = new PasswordJPanel(" password membre ? ", memberPassword, windowWidth);
			addFilmJP.add(passwordJPanel);
			titleJPanel = new InputJPanel(" titre ? ", filmTitle, windowWidth);
			addFilmJP.add(titleJPanel);
			kindJPanel = new InputJPanel(" genre ? ", filmKind, windowWidth);
			addFilmJP.add(kindJPanel);
			directorJPanel = new InputJPanel(" réalisateur ? ", filmDirector, windowWidth);
			addFilmJP.add(directorJPanel);
			scenaristJPanel = new InputJPanel(" scénariste ? ", filmScenarist, windowWidth);
			addFilmJP.add(scenaristJPanel);
			nbPagesJPanel = new InputJPanel(" durée ? ", filmDuration, windowWidth);
			addFilmJP.add(nbPagesJPanel);
			JButton confirm = new JButton("Valider ajouter item film"); 
			confirm.setPreferredSize(new Dimension(windowWidth-20, 25));
			confirm.addActionListener(new AddItemFilmAction());
			addFilmJP.add(confirm);
			addFilmJP.setVisible(false);
			interactionWindow.getContentPane().removeAll();
			interactionWindow.repaint();
			interactionWindow.setSize(windowWidth + 100, 320);
			interactionWindow.getContentPane().add(addFilmJP);
			addFilmJP.setVisible(true);			
			interactionWindow.setVisible(true);			
			interactionWindow.repaint();
		}

		class AddItemFilmAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					memberLogin = loginJPanel.getEntree(); 
					memberPassword = passwordJPanel.getPassword(); 
					filmTitle = titleJPanel.getEntree(); 
					filmKind = kindJPanel.getEntree(); 
					filmDirector = directorJPanel.getEntree(); 
					filmScenarist = scenaristJPanel.getEntree(); 
					filmDuration = nbPagesJPanel.getEntree(); 
					sn.addItemFilm(memberLogin, memberPassword, filmTitle, filmKind, filmDirector, filmScenarist, new Integer(filmDuration));
				}
				catch (Exception exception) {
					JOptionPane.showMessageDialog(interactionWindow, "Exception dans addItemFilm :   "  + exception);
				}
			}
		}
	}




	private class ReviewItem   implements ActionListener {
		/**
		 * @uml.property  name="pseudoJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel pseudoJPanel;
		/**
		 * @uml.property  name="passwordJPanel"
		 * @uml.associationEnd  
		 */
		PasswordJPanel passwordJPanel;
		/**
		 * @uml.property  name="titleJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel titleJPanel;
		/**
		 * @uml.property  name="noteJPanel"
		 * @uml.associationEnd  
		 */
		InputJPanel noteJPanel;
		/**
		 * @uml.property  name="commentJScrollPane"
		 * @uml.associationEnd  
		 */
		TextJScrollPane commentJScrollPane;

		String type;

		public ReviewItem (String type) {
			this.type = type;
		}
		public void actionPerformed(ActionEvent e) {
			JPanel reviewerJP = new JPanel();
			commentJScrollPane = new TextJScrollPane(" commentaire ? ",  comment, true, windowWidth); // comment ?
			reviewerJP.add(commentJScrollPane);
			JPanel loginPwdAndTitleJPanel = new JPanel();
			loginPwdAndTitleJPanel.setLayout(new GridLayout(5,1, 4, 4));
			pseudoJPanel = new InputJPanel(" pseudo membre ? ", memberLogin, windowWidth); // member's login ?
			loginPwdAndTitleJPanel.add(pseudoJPanel);
			passwordJPanel = new PasswordJPanel(" password membre ? ", memberPassword, windowWidth); // member's password ?
			loginPwdAndTitleJPanel.add(passwordJPanel);
			titleJPanel = new InputJPanel(" titre ? ", bookTitle, windowWidth); // title ?
			loginPwdAndTitleJPanel.add(titleJPanel);
			noteJPanel = new InputJPanel(" note ? ", mark, windowWidth); // mark ?
			loginPwdAndTitleJPanel.add(noteJPanel);
			JButton valider = new JButton("Valider reviewer item " + type); // confirm review item ?
			valider.setPreferredSize(new Dimension(windowWidth-20, 25));
			valider.addActionListener(new ReviewItemAction());
			loginPwdAndTitleJPanel.add(valider);
			reviewerJP.add(loginPwdAndTitleJPanel);				
			reviewerJP.setVisible(false);
			interactionWindow.getContentPane().removeAll();
			interactionWindow.repaint();
			interactionWindow.setSize(windowWidth + 100, 470);
			interactionWindow.getContentPane().add(reviewerJP);
			reviewerJP.setVisible(true);			
			interactionWindow.setVisible(true);			
			interactionWindow.repaint();
		}

		class ReviewItemAction implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				try {
					memberLogin = pseudoJPanel.getEntree(); 
					memberPassword = passwordJPanel.getPassword(); 
					bookTitle = titleJPanel.getEntree(); 
					mark = noteJPanel.getEntree();
					comment = commentJScrollPane.getText();
					if (type.equals("livre")) //book
						sn.reviewItemBook(memberLogin, memberPassword, bookTitle, new Float(mark), comment);
					if (type.equals("film")) // film
						sn.reviewItemFilm(memberLogin, memberPassword, bookTitle, new Float(mark), comment);

				}
				catch (Exception exception) {
					if (type.equals("livre")) // book
						JOptionPane.showMessageDialog(interactionWindow, "Exception dans reviewItemBook :   "  + exception);
					if (type.equals("film")) // film
						JOptionPane.showMessageDialog(interactionWindow, "Exception dans reviewItemFilm :   "  + exception);
				}
			}
		}
	}







	/**
	 * Creates a new <i>SocialNetwork</i> with <ul>
	 * <li>3 users (login/pwd) : Paul/paul, Antoine/antoine, Alice/alice</li>
	 * <li>3 books (title) : "Lignes de faille","La peste", "Guerre et Paix", "Le train sifflera trois fois"</li>
	 * <li>4 films (title) : "Le train sifflera trois fois", "Avant l'aube", "Le discours d'un roi", "Black Swan", "Guerre et Paix"</li>
	 * </ul>
	 * and some opinions  :<ul>
	 * <li>Book "La Peste" : Antoine (1.5),Alice (2.5)</li>
	 * <li>Book "Lignes de faille" : Alice (1.5)</li>
	 * <li>Book "Guerre et paix" : Alice (2.0), Antoine (4.5)</li>
	 * <li>Film "Black Swan" : Paul (2.5)</li>
	 * <li>Film "Avant l'aube" : Antoine (3.0), Paul (2.5)</li>
	 * <li>Film "Guerre et paix" : Alice (4.2), Paul (4.4)</li>
	 * </ul>
	 * Then add an opinion by a member who already gave one for the same item : Book "La Peste" / Antoine (3.8)</br>
	 * And finally launches an interactive HMI that gives access to the main actions on this <i>SocialNetwork</i>.
	 * @param args not used
	 */
	public static void main (String [] args) {

		try {
			ISocialNetwork sn = new SocialNetwork(); // the SocialNetwork to interact with

			HMI ihm = new HMI(); 
			ihm.setTopic(sn);

			// add 3 members
			sn.addMember("Paul", "paul", "lecteur impulsif");
			sn.addMember("Antoine", "antoine", "grand amoureux de littérature");
			sn.addMember("Alice", "alice", "passionnée de romans policiers");


			// add 3 books and 4 films
			sn.addItemBook("Alice", "alice", "Lignes de faille", "roman", "Nancy Huston", 220);
			sn.addItemFilm("Alice", "alice", "Le train sifflera trois fois", "western 1952", "Fred Zinnemann", "Carl Foreman", 85);
			sn.addItemBook("Paul", "paul", "La peste", "roman", " Albert Camus", 336);
			sn.addItemFilm("Paul", "paul", "Avant l'aube", "thriller 2011", "Raphael Jacoulot", "Lise Macheboeuf et Raphael Jacoulot", 104);
			sn.addItemBook("Antoine", "antoine", "Guerre et Paix", "roman", "Leon Tosltoi", 1247);
			sn.addItemFilm("Antoine", "antoine", "Le discours d'un roi", "drame historique 2010", "Tom Hooper", "David Seidler", 118);
			sn.addItemFilm("Alice", "alice", "Black Swan", "drame 2010", "Darren Aronofsky", "John McLaughlin et Mark Heyman et Andres Heinz", 103);
			sn.addItemBook("Alice", "alice", "Le train sifflera trois fois", "roman", " J. W. Cunningham", 257);
			sn.addItemFilm("Paul", "paul", "Guerre et Paix", "aventure historique", "King Vidor", "Bridget Boland, Robert Westbery", 200);



			// review some books and films
			sn.reviewItemBook("Antoine", "antoine", "La Peste", 1.5f, "un peu daté");	
			sn.reviewItemFilm("Paul", "paul", "Black Swan", 2.5f, "pour la performance...");
			sn.reviewItemFilm("Antoine", "antoine", "Avant l'aube", 3.0f, "tout le monde fume...");	
			sn.reviewItemBook("Alice", "alice", "Lignes de faille", 1.5f, "famille attachante");	
			// review a film and a book that were already reviewed by somebody else
			sn.reviewItemFilm("Paul", "paul", "Avant l'aube", 2.5f, "mitigé");	
			sn.reviewItemBook("Alice", "alice", "La Peste", 2.5f, "   ");	
			// several reviews of a film and a book with the same title
			sn.reviewItemBook("Alice", "alice", "Guerre et Paix", 2.0f, "un peu long");	
			sn.reviewItemFilm("Alice", "alice", "Guerre et Paix", 4.2f, "on ne voit pas le temps passer");	
			sn.reviewItemBook("Antoine", "antoine", "Guerre et Paix", 4.5f, "parfait pour une semaine pluvieuse");	
			sn.reviewItemFilm("Paul", "paul", "Guerre et Paix", 4.4f, "choisir une salle confortable");	
			// review a book that was already reviewed by the same member
			sn.reviewItemBook("Antoine", "antoine", "La Peste", 3.8f, "bien meilleur à la relecture");	

			System.out.println(sn);
		}
		catch (Exception e) {
			System.out.println("Exception inattendue : " + e);
			e.printStackTrace();
		}

	}


}





