package me.alexishaldy.util;

import me.alexishaldy.classes.Book;
import me.alexishaldy.classes.Library;
import me.alexishaldy.classes.SortType;
import me.alexishaldy.classes.User;

public class CmdManager {
	
	public void sendCommand(String a[]) {
		Library lib = Library.getLibrary();
		if (a[0].equalsIgnoreCase("help") || a[0].equalsIgnoreCase("h")) {
			if (a.length>1) {
				Utils.displayHelp(a[1]);
			} else
				Utils.displayHelp();
		}
		
		if (a[0].equalsIgnoreCase("mkuser") || a[0].equalsIgnoreCase("mku")) {
			if (a.length>1) {
				String n1="";
				String n2 = "";
				String n3 = "";
				String n4 = "";
				String n5 = "";
				int count = 0;
				for (int i=1;i<a.length;i++) {
					if (i%5==1)
						n1=a[i];
					else if (i%5==2)
						n2=a[i];
					else if (i%5==3)
						n3=a[i];
					else if (i%5==4)
						n4=a[i];
					else if (i%5==0)
						n5=a[i];
					if (!n1.isEmpty() && !n2.isEmpty() && !n3.isEmpty() && !n4.isEmpty() && !n5.isEmpty()) {
						count++;
						lib.addUser(new User(n1, n2, n3, n4, n5));
						Utils.display("Utilisateur "+count+" créé !");
						n1="";
						n2="";
						n3="";
						n4="";
						n5="";
					}
				}
			} else {
				Utils.display("Création en cours d'un nouvel utilisateur\nSi vous voulez annuler taper '-exit'");
				String name = Reader.readString("Donnez lui un prénom\n");
				if (!name.equalsIgnoreCase("-exit")) {
					String lastname = Reader.readString("Donnez lui un nom\n");
					if (!lastname.equalsIgnoreCase("-exit")) {
						String username = Reader.readString("Donnez lui un pseudo\n");
						if (!username.equalsIgnoreCase("-exit")) {
							String email = Reader.readEmail("Donnez lui un email\n");
							if (!email.equalsIgnoreCase("-exit")) {
								String tel = Reader.readNumTel("Donnez lui un numéro de téléphone\n");
								if (!tel.equalsIgnoreCase("-exit")) {
									lib.addUser(new User(username, name, lastname, email, tel));
									Utils.display("Utilisateur "+username+" créé !");
								}
							}
						}
					}
				}
			}
		}
		if (a[0].equalsIgnoreCase("mkbook") || a[0].equalsIgnoreCase("mkb")) {
			if (a.length>1) {
				String title="";
				String author = "";
				int year= -1;
				String desc= "";
				String edit= "";
				int numedit= -1;
				int count = 0;
				for (int i=1;i<a.length;i++) {
					if (i%6==1)
						title=a[i];
					else if (i%6==2)
						author=a[i];
					else if (i%6==3 && Utils.isInt(a[i]))
						year=Utils.getInt(a[i]);	
					else if (i%6==4)
						desc=a[i];
					else if (i%6==5 && Utils.isInt(a[i]))
						numedit=Utils.getInt(a[i]);
					else if (i%6==0)
						edit=a[i];
						
					if (!title.isEmpty() && !author.isEmpty() && year>-1 && !desc.isEmpty() && !edit.isEmpty() && numedit>-1) {
						count++;
						lib.addBook(new Book(title, author, year, desc, numedit, edit));
						Utils.display("Livre "+count+" créé !");
						title="";
						author = "";
						year= -1;
						desc= "";
						edit= "";
						numedit= -1;
					}
				}
			} else {
				Utils.display("Création en cours d'un nouveau livre\nSi vous voulez annuler taper '-exit'");
				String title = Reader.readString("Donnez lui un titre\n");
				if (!title.equalsIgnoreCase("-exit")) {
					String author = Reader.readString("Donnez lui un auteur qui l'a écrit\n");
					if (!author.equalsIgnoreCase("-exit")) {
						int year = Reader.readInt("Donnez lui une année de création\n");
						String desc = Reader.readString("Donnez lui une description\n");
						if (!desc.equalsIgnoreCase("-exit")) {
							int numedit = Reader.readInt("Donnez lui un numéro d'édition\n");
								String edit = Reader.readString("Donnez lui une édition\n");
								if (!edit.equalsIgnoreCase("-exit")) {
									String id = Reader.readString("Donnez lui un id d'user si vous voulez que ce livre lui appartienne dès sa création ou mettez -1 pour ignorer cette option\n");
									if (id.equalsIgnoreCase("-1")) {
										lib.addBook(new Book(title, author, year, desc, numedit, edit));
										Utils.display("Livre créé !");
									} else {
										lib.addBook(new Book(title, author, year, desc, numedit, edit, lib.getUserById(id).getIdentityId()));
										Utils.display("Livre créé !");
									}
								}								
						}
						
						
						
					}
				}
					
			}
		}
		
		if (a[0].equalsIgnoreCase("rmuser") || a[0].equalsIgnoreCase("rmu")) {
			if (a.length>1) {
				String type="";
				String idop = "";
				int count = 0;
				for (int i=1;i<a.length;i++) {
					if (i%2==1)
						type=a[i];
					else if (i%2==0)
						idop=a[i];
					if (!type.isEmpty() && !idop.isEmpty() && (type.equalsIgnoreCase("id") || type.equalsIgnoreCase("username"))) {
						count++;
						if (type.equalsIgnoreCase("id")) {
							if (!lib.removeUser(lib.getUserById(idop))) {
								Utils.display("L'utilisateur n'a pas pu être suprimmé...");
							}
						} else if (type.equalsIgnoreCase("username")) {
							if (!lib.removeUser(lib.getUserByUsername(idop))) {
								Utils.display("L'utilisateur n'a pas pu être suprimmé...");
							}
						}
						type="";
						idop = "";
					}
					if (count>0)
						Utils.display("Vous avez supprimé "+count+" utilisateur"+(count>1 ? "s" : ""));
				}
			} else {
				String choix = Reader.readBoolean("id username -exit", "Par le pseudo ou l'id ? (id/username)\nSi vous voulez annuler taper '-exit'\n");
				if (choix.equalsIgnoreCase("id")) {
					String id = Reader.readString("Donnez-moi son ID\n");
					if (id.equalsIgnoreCase("-exit")) {
						if (lib.getUserById(id)!=null) {
							if (lib.removeUser(lib.getUserById(id))) {
								Utils.display("L'utilisateur a bien été suprimmé !");
							} else {
								Utils.display("L'utilisateur n'a pas pu être suprimmé...");
							}
						} else
							Utils.display("Cet ID n'existe pas...");
					}
				} else if (choix.equalsIgnoreCase("username")) {
					String pseudo = Reader.readString("Donnez-moi son pseudo\n");
					if (pseudo.equalsIgnoreCase("-exit")) {
						if (lib.getUserByUsername(pseudo)!=null) {
							if (lib.removeUser(lib.getUserByUsername(pseudo))) {
								Utils.display("L'utilisateur a bien été suprimmé !");
							} else {
								Utils.display("L'utilisateur n'a pas pu être suprimmé...");
							}
						} else
							Utils.display("Ce pseudo n'existe pas...");
					}
				}
			}
		}
		
		if (a[0].equalsIgnoreCase("rmbook") || a[0].equalsIgnoreCase("rmb")) {
			if (a.length>1) {
				String type="";
				String idop = "";
				int count = 0;
				for (int i=1;i<a.length;i++) {
					if (i%2==1)
						type=a[i];
					else if (i%2==0)
						idop=a[i];
					if (!type.isEmpty() && !idop.isEmpty() && (type.equalsIgnoreCase("id") || type.equalsIgnoreCase("username"))) {
						count++;
						if (type.equalsIgnoreCase("id")) {
							if (!lib.removeUser(lib.getUserById(idop))) {
								Utils.display("L'utilisateur n'a pas pu être suprimmé...");
							}
						} else if (type.equalsIgnoreCase("username")) {
							if (!lib.removeUser(lib.getUserByUsername(idop))) {
								Utils.display("L'utilisateur n'a pas pu être suprimmé...");
							}
						}
						type="";
						idop = "";
					}
					if (count>0)
						Utils.display("Vous avez supprimé "+count+" utilisateur"+(count>1 ? "s" : ""));
				}
			} else {
				String choix = Reader.readBoolean("title author year title_author title_author_numedition desc -exit", "Par quel façon ? (title:author:year:title_author:title_author_numedition:desc)\nSi vous voulez annuler taper '-exit'\n");
				if (choix.equalsIgnoreCase("title")) {
					String title = Reader.readString("Donnez-moi son titre\n");
					if (!title.equalsIgnoreCase("-exit")) {
						String s[] = new String[1];
						s[0]=title;
						if (lib.getBook(SortType.title, s)!=null) {
							if (lib.removeBook(lib.getBook(SortType.title, s))) {
								Utils.display("Le livre a bien été suprimmé !");
							} else {
								Utils.display("Le livre n'a pas pu être suprimmé...");
							}
						} else
							Utils.display("Il n'y a pas de livre avec ce titre...");
					}
				} else if (choix.equalsIgnoreCase("author")) {
					String auth = Reader.readString("Donnez-moi son auteur\n");
					if (!auth.equalsIgnoreCase("-exit")) {
						String s[] = new String[1];
						s[0]=auth;
						if (lib.getBook(SortType.author, s)!=null) {
							if (lib.removeBook(lib.getBook(SortType.author, s))) {
								Utils.display("Le livre a bien été suprimmé !");
							} else {
								Utils.display("Le livre n'a pas pu être suprimmé...");
							}
						} else
							Utils.display("Il n'y a pas de livre avec cet auteur...");
					}
				} else if (choix.equalsIgnoreCase("year")) {
					int year = Reader.readInt("Donnez-moi son année de parution\n");
					if (year!=-1) {
						String s[] = new String[1];
						s[0]=String.valueOf(year);
						if (lib.getBook(SortType.year, s)!=null) {
							if (lib.removeBook(lib.getBook(SortType.year, s))) {
								Utils.display("Le livre a bien été suprimmé !");
							} else {
								Utils.display("Le livre n'a pas pu être suprimmé...");
							}
						} else
							Utils.display("Il n'y a pas de livre qui existe qui ont cette date de parution...");
					}
				} else if (choix.equalsIgnoreCase("title_author")) {
					String title = Reader.readString("Donnez-moi son titre\n");
					String auth = Reader.readString("Donnez-moi son auteur\n");
					if (!auth.equalsIgnoreCase("-exit") && !title.equalsIgnoreCase("-exit")) {
						String s[] = new String[2];
						s[0]=title;
						s[1]=auth;
						if (lib.getBook(SortType.title_author, s)!=null) {
							if (lib.removeBook(lib.getBook(SortType.title_author, s))) {
								Utils.display("Le livre a bien été suprimmé !");
							} else {
								Utils.display("Le livre n'a pas pu être suprimmé...");
							}
						} else
							Utils.display("Il n'y a pas de livre qui existe qui ont ces paramètres...");
					}
				} else if (choix.equalsIgnoreCase("title_author_numedition")) {
					String title = Reader.readString("Donnez-moi son titre\n");
					String auth = Reader.readString("Donnez-moi son auteur\n");
					int numedit = Reader.readInt("Donnez-moi son numéro d'édition\n");
					if (!auth.equalsIgnoreCase("-exit") && !title.equalsIgnoreCase("-exit") && numedit!=-1) {
						String s[] = new String[3];
						s[0]=title;
						s[1]=auth;
						s[2]=String.valueOf(numedit);
						if (lib.getBook(SortType.title_author_numedition, s)!=null) {
							if (lib.removeBook(lib.getBook(SortType.title_author_numedition, s))) {
								Utils.display("Le livre a bien été suprimmé !");
							} else {
								Utils.display("Le livre n'a pas pu être suprimmé...");
							}
						} else
							Utils.display("Il n'y a pas de livre qui existe qui ont ces paramètres...");
					}
				} else if (choix.equalsIgnoreCase("desc")) {
					String desc = Reader.readString("Donnez-moi son titre\n");
					if (!desc.equalsIgnoreCase("-exit")) {
						String s[] = new String[1];
						s[0]=desc;
						if (lib.getBook(SortType.desc, s)!=null) {
							if (lib.removeBook(lib.getBook(SortType.desc, s))) {
								Utils.display("Le livre a bien été suprimmé !");
							} else {
								Utils.display("Le livre n'a pas pu être suprimmé...");
							}
						} else
							Utils.display("Il n'y a pas de livre qui existe avec cette description...");
					}
				}
			}
		}
		
		if (a[0].equalsIgnoreCase("edituser") || a[0].equalsIgnoreCase("eu")) {
			if (a.length>1) {
				String type="";
				String idop = "";
				String email = "";
				String tel = "";
				int count = 0;
				for (int i=1;i<a.length;i++) {
					if (i%2==1)
						type=a[i];
					else if (i%2==0)
						idop=a[i];
					if (!type.isEmpty() && !idop.isEmpty() && (type.equalsIgnoreCase("id") || type.equalsIgnoreCase("username")) && !email.isEmpty() && !tel.isEmpty()) {
						count++;
						User u = null;
						if (type.equalsIgnoreCase("id")) {
							u = lib.getUserById(type);
						} else if (type.equalsIgnoreCase("username")) {
							u = lib.getUserByUsername(type);
						}
						u.setEmail(email);
						u.setTel(tel);
						type="";
						idop = "";
						email = "";
						tel = "";
					}
					if (count>0)
						Utils.display("Vous avez éditer "+count+" utilisateur"+(count>1 ? "s" : ""));
				}
			} else {
				String choix = Reader.readBoolean("id username -exit", "Par le pseudo ou l'id ? (id/username)\nSi vous voulez annuler taper '-exit'\n");
				if (choix.equalsIgnoreCase("id")) {
					String id = Reader.readString("Donnez-moi son ID\n");
					if (!id.equalsIgnoreCase("-exit")) {
						if (lib.getUserById(id)!=null) {
							User u = lib.getUserById(id);
							String email = Reader.readEmail("Donnez-moi son nouvel email (Ancien: "+u.getEmail()+")\n");
							if (!email.equalsIgnoreCase("-exit")) {
								String tel = Reader.readNumTel("Donnez-moi son nouveau numéro de téléphone (Ancien: "+u.getTel()+")\n");
								if (!tel.equalsIgnoreCase("-exit")) {
									u.setEmail(email);
									u.setTel(tel);
									Utils.display("L'email et le tel ont bien été changés");
								}
							}
						} else
							Utils.display("Cet ID n'existe pas...");
					}
				} else if (choix.equalsIgnoreCase("username")) {
					String pseudo = Reader.readString("Donnez-moi son pseudo\n");
					if (!pseudo.equalsIgnoreCase("-exit")) {
						if (lib.getUserByUsername(pseudo)!=null) {
								User u = lib.getUserByUsername(pseudo);
								String email = Reader.readEmail("Donnez-moi son nouvel email (Ancien: "+u.getEmail()+")\n");
								if (!email.equalsIgnoreCase("-exit")) {
									String tel = Reader.readNumTel("Donnez-moi son nouveau numéro de téléphone (Ancien: "+u.getTel()+")\n");
									if (!tel.equalsIgnoreCase("-exit")) {
										u.setEmail(email);
										u.setTel(tel);
										Utils.display("L'email et le tel ont bien été changés");
									}
								}
						} else
							Utils.display("Ce pseudo n'existe pas...");
					}
				}
			}						
		}
		
		if (a[0].equalsIgnoreCase("displayuser") || a[0].equalsIgnoreCase("dpu")) {
			Utils.display(lib.listUserAndBooks());
		}
		
		if (a[0].equalsIgnoreCase("displaybook") || a[0].equalsIgnoreCase("dpb")) {
			Utils.display(lib.listBook(SortType.Number));
		}
	}
}























