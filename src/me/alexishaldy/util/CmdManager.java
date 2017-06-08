package me.alexishaldy.util;

import me.alexishaldy.bdd.Bdd;
import me.alexishaldy.classes.Library;
import me.alexishaldy.enumerator.Reason;
import me.alexishaldy.enumerator.SortType;

public class CmdManager {
	
	/**
	 * Permet d'envoyer une commande à executer
	 * @param a Commande séparée en arguments par espace
	 */
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
				String n1=a[1];
				String n2=a[2];
				String n3=a[3];
				String n4=a[4];
				String n5=a[5];					
				if (lib.getUserIdByUsername(n1)==-1) {
					String s[] = new String[6];
					s[0] = n1;
					s[1] = n2;
					s[2] = n3;
					s[3] = n4;
					s[4] = n5;
					s[5] = ""+lib.getId();
					new Bdd().sendCmd(Reason.adduser, s);
				} else {
					Utils.display("Erreur, ce pseudo est déjà pris !");
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
									String s[] = new String[6];
									s[0] = name.replace("'", "\\'");
									s[1] = lastname.replace("'", "\\'");
									s[2] = username.replace("'", "\\'");
									s[3] = email.replace("'", "\\'");
									s[4] = tel.replace("'", "\\'");
									s[5] = ""+lib.getId();
									new Bdd().sendCmd(Reason.adduser, s);
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
				title=a[1].replaceAll("_", " ");
				author=a[2].replaceAll("_", " ");
				year=Utils.getInt(a[3]);	
				desc=a[4].replaceAll("_", " ");
				numedit=Utils.getInt(a[5]);
				edit=a[6].replaceAll("_", " ");
				String s[] = new String[7];
				s[0] = title.replace("'", "\\'");
				s[1] = author.replace("'", "\\'");
				s[2] = ""+year;
				s[3] = desc.replace("'", "\\'");
				s[4] = ""+numedit;
				s[5] = edit.replace("'", "\\'");
				s[6] = ""+lib.getId();
				new Bdd().sendCmd(Reason.addbook, s);
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
									String s[] = new String[7];
									s[0] = title.replace("'", "\\'");
									s[1] = author.replace("'", "\\'");
									s[2] = ""+year;
									s[3] = desc.replace("'", "\\'");
									s[4] = ""+numedit;
									s[5] = edit.replace("'", "\\'");
									s[6] = ""+lib.getId();
									new Bdd().sendCmd(Reason.addbook, s);
								}								
						}
						
						
						
					}
				}
					
			}
		}
		
		if (a[0].equalsIgnoreCase("rmuser") || a[0].equalsIgnoreCase("rmu")) {
			if (a.length>1) {
				int id = -1;
				if (a[1].equalsIgnoreCase("id")) {
					id = Integer.parseInt(a[2]);
				} else if (a[1].equalsIgnoreCase("username")) {
					id = lib.getUserIdByUsername(a[2]);
				}
				if (id==-1) {
					Utils.display("L'utilisateur n'a pas pu être suprimmé...");
				} else {
					lib.removeUser(id);
					Utils.display("L'utilisateur a été supprimé");
				}
			} else {
				String choix = Reader.readBoolean("id username -exit", "Par le pseudo ou l'id ? (id/username)\nSi vous voulez annuler taper '-exit'\n");
				if (choix.equalsIgnoreCase("id")) {
					int id = Reader.readInt("Donnez-moi son ID\n");
					if (id==-1) {
						Utils.display("L'utilisateur n'a pas pu être suprimmé...");
					} else {
						lib.removeUser(id);
						Utils.display("L'utilisateur a été supprimé");
					}
				} else if (choix.equalsIgnoreCase("username")) {
					String pseudo = Reader.readString("Donnez-moi son pseudo\n");
					if (!pseudo.equalsIgnoreCase("-exit")) {
						int id = lib.getUserIdByUsername(pseudo);					
						if (id==-1) {
							Utils.display("L'utilisateur n'a pas pu être suprimmé...");
						} else {
							lib.removeUser(id);
							Utils.display("L'utilisateur a été supprimé");
						}
					}
				}
			}
		}
		
		if (a[0].equalsIgnoreCase("rmbook") || a[0].equalsIgnoreCase("rmb")) {
			if (a.length>1) {
				String type="";
				String idop = "";
				type=a[1];
				if (a.length>=2) {
					for (int i=2;i<a.length;i++) {
						idop+=a[i]+" ";
					}
				}
				int id = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (type+"§"+idop.replaceAll(" ", "§").replaceAll("_", " ")).split("§")));
				if (id==-1) {
					Utils.display("Livre inexistant: "+a[2]);
				} else {
					new Bdd().sendCmd(Reason.rmbook, ""+id);
					Utils.display("livre "+a[2]+" supprimé !");
				}
			} else {
				String choix = Reader.readBoolean("title author year title_author title_author_numedition desc -exit", "Par quel façon ? (title:author:year:title_author:title_author_numedition:desc)\nSi vous voulez annuler taper '-exit'\n");
				String s[] = null;
				if (choix.equalsIgnoreCase("title")) {
					String title = Reader.readString("Donnez-moi son titre\n");
					if (!title.equalsIgnoreCase("-exit")) {
						s = new String[2];
						s[0]=choix;
						s[1]=title;
					}
				} else if (choix.equalsIgnoreCase("author")) {
					String auth = Reader.readString("Donnez-moi son auteur\n");
					if (!auth.equalsIgnoreCase("-exit")) {
						s = new String[2];
						s[0]=choix;
						s[1]=auth;
					}
				} else if (choix.equalsIgnoreCase("year")) {
					int year = Reader.readInt("Donnez-moi son année de parution\n");
					if (year!=-1) {
						s = new String[2];
						s[0]=choix;
						s[1]=String.valueOf(year);
					}
				} else if (choix.equalsIgnoreCase("title_author")) {
					String title = Reader.readString("Donnez-moi son titre\n");
					String auth = Reader.readString("Donnez-moi son auteur\n");
					if (!auth.equalsIgnoreCase("-exit") && !title.equalsIgnoreCase("-exit")) {
						s = new String[3];
						s[0]=choix;
						s[1]=title;
						s[2]=auth;
					}
				} else if (choix.equalsIgnoreCase("title_author_numedition")) {
					String title = Reader.readString("Donnez-moi son titre\n");
					String auth = Reader.readString("Donnez-moi son auteur\n");
					int numedit = Reader.readInt("Donnez-moi son numéro d'édition\n");
					if (!auth.equalsIgnoreCase("-exit") && !title.equalsIgnoreCase("-exit") && numedit!=-1) {
						s = new String[4];
						s[0]=choix;
						s[1]=title;
						s[2]=auth;
						s[3]=String.valueOf(numedit);
					}
				} else if (choix.equalsIgnoreCase("desc")) {
					String desc = Reader.readString("Donnez-moi son titre\n");
					if (!desc.equalsIgnoreCase("-exit")) {
						s = new String[2];
						s[0]=choix;
						s[1]=desc;
					}
				}
				for (int i=0;i<s.length;i++) {
					s[i] = s[i].replace("'", "\\'");
				}
				int id = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, s));
				if (id==-1) {
					Utils.display("Livre inexistant");
				} else {
					new Bdd().sendCmd(Reason.rmbook, ""+id);
					Utils.display("Le livre a bien été supprimé !");
				}
			}
		}
		
		if (a[0].equalsIgnoreCase("edituser") || a[0].equalsIgnoreCase("eu")) {
			if (a.length>1) {
				try {
					int id = -1;
					if (a[1].equalsIgnoreCase("id")) {
						id = Integer.parseInt(a[2]);
					} else if (a[1].equalsIgnoreCase("username")) {
						id = lib.getUserIdByUsername(a[2]);
					}
					if (id==-1) {
						Utils.display("L'utilisateur n'a pas été trouvé...");
					} else {
						new Bdd().sendCmd(Reason.edituser, (id+"§"+a[3]+"§"+a[4]).split("§"));
						Utils.display("L'utilisateur a bien été modifié");
					}
				} catch (Exception e) {
					Utils.display("Erreur, champs manquants ou erronés");
				}
			} else {
				String choix = Reader.readBoolean("id username -exit", "Par le pseudo ou l'id ? (id/username)\nSi vous voulez annuler taper '-exit'\n");
				if (choix.equalsIgnoreCase("id")) {
					int id = Reader.readInt("Donnez-moi son ID\n");
					if (id!=-1) {
						String email = Reader.readEmail("Donnez-moi son nouvel email\n");
						if (!email.equalsIgnoreCase("-exit")) {
							String tel = Reader.readNumTel("Donnez-moi son nouveau numéro de téléphone\n");
							if (!tel.equalsIgnoreCase("-exit")) {
								new Bdd().sendCmd(Reason.edituser, (id+"§"+email+"§"+tel).split("§"));
								Utils.display("L'email et le tel ont bien été changés");
							}
						}
					}
				} else if (choix.equalsIgnoreCase("username")) {
					String pseudo = Reader.readString("Donnez-moi son pseudo\n");
					if (!pseudo.equalsIgnoreCase("-exit")) {
						if (lib.getUserIdByUsername(pseudo)!=-1) {
							String email = Reader.readEmail("Donnez-moi son nouvel email\n");
							if (!email.equalsIgnoreCase("-exit")) {
								String tel = Reader.readNumTel("Donnez-moi son nouveau numéro de téléphone\n");
								if (!tel.equalsIgnoreCase("-exit")) {
									new Bdd().sendCmd(Reason.edituser, (lib.getUserIdByUsername(pseudo)+"§"+email+"§"+tel).split("§"));
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
			Utils.display(lib.listAllBook(SortType.Number));
		}
		
		if (a[0].equalsIgnoreCase("displayrenter") || a[0].equalsIgnoreCase("dpr")) {
			Utils.display(lib.listRenterBook());
		}
		
		if (a[0].equalsIgnoreCase("takebook") || a[0].equalsIgnoreCase("tb")) {
			if (a.length>1) {
				String type="";
				String idop = "";
				String type2="";
				String idbook = "";
				type=a[1];
				idop=a[2];
				type2=a[3];
				idbook=a[4];
				int idu = -1;
				if (type.equalsIgnoreCase("id")) {
					idu = Integer.parseInt(idop);
				} else if (type.equalsIgnoreCase("username")) {
					idu = lib.getUserIdByUsername(idop);
				}
				int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (type2+"§"+idbook).split("§")));
				new Bdd().sendCmd(Reason.takebook, (idu+"§"+idb).split("§"));					
				
			} else {
				int idu = -1;
				String choix = Reader.readBoolean("id username -exit", "Par le pseudo ou l'id ? (id/username)\nSi vous voulez annuler taper '-exit'\n");
				if (choix.equalsIgnoreCase("id")) {
					idu = Reader.readInt("Donnez-moi son ID\n");
				} else if (choix.equalsIgnoreCase("username")) {
					String pseudo = Reader.readString("Donnez-moi son pseudo\n");
					if (!pseudo.equalsIgnoreCase("-exit")) {
						idu = Integer.parseInt(new Bdd().sendCmd(Reason.searchuser, pseudo));
					}
				}
				
				choix = Reader.readBoolean("title author year title_author title_author_numedition desc -exit", "Par quel façon ? (title:author:year:title_author:title_author_numedition:desc)\nSi vous voulez annuler taper '-exit'\n");
				if (choix.equalsIgnoreCase("title")) {
					String title = Reader.readString("Donnez-moi son titre\n");
					if (!title.equalsIgnoreCase("-exit")) {
						int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (choix+"§"+title).split("§")));
						new Bdd().sendCmd(Reason.takebook, (idu+"§"+idb).split("§"));
					}
				} else if (choix.equalsIgnoreCase("author")) {
					String auth = Reader.readString("Donnez-moi son auteur\n");
					if (!auth.equalsIgnoreCase("-exit")) {
						int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (choix+"§"+auth).split("§")));
						new Bdd().sendCmd(Reason.takebook, (idu+"§"+idb).split("§"));
					}
				} else if (choix.equalsIgnoreCase("year")) {
					int year = Reader.readInt("Donnez-moi son année de parution\n");
					if (year!=-1) {
						int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (choix+"§"+year).split("§")));
						new Bdd().sendCmd(Reason.takebook, (idu+"§"+idb).split("§"));
					}
				} else if (choix.equalsIgnoreCase("title_author")) {
					String title = Reader.readString("Donnez-moi son titre\n");
					String auth = Reader.readString("Donnez-moi son auteur\n");
					if (!auth.equalsIgnoreCase("-exit") && !title.equalsIgnoreCase("-exit")) {
						int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (choix+"§"+title+"§"+auth).split("§")));
						new Bdd().sendCmd(Reason.takebook, (idu+"§"+idb).split("§"));
					}
				} else if (choix.equalsIgnoreCase("title_author_numedition")) {
					String title = Reader.readString("Donnez-moi son titre\n");
					String auth = Reader.readString("Donnez-moi son auteur\n");
					int numedit = Reader.readInt("Donnez-moi son numéro d'édition\n");
					if (!auth.equalsIgnoreCase("-exit") && !title.equalsIgnoreCase("-exit") && numedit!=-1) {
						int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (choix+"§"+title+"§"+auth+"§"+numedit).split("§")));
						new Bdd().sendCmd(Reason.takebook, (idu+"§"+idb).split("§"));
					}
				} else if (choix.equalsIgnoreCase("desc")) {
					String desc = Reader.readString("Donnez-moi sa description\n");
					if (!desc.equalsIgnoreCase("-exit")) {
						int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (choix+"§"+desc).split("§")));
						new Bdd().sendCmd(Reason.takebook, (idu+"§"+idb).split("§"));
					}
				}
			}
		}
		
		if (a[0].equalsIgnoreCase("returnbook") || a[0].equalsIgnoreCase("rb")) {
			if (a.length>1) {
				String type="";
				String idop = "";
				String type2="";
				String idbook = "";
				type=a[1];
				idop=a[2];
				type2=a[3];
				idbook=a[4];
				int idu = -1;
				if (type.equalsIgnoreCase("id")) {
					idu = Integer.parseInt(idop);
				} else if (type.equalsIgnoreCase("username")) {
					idu = lib.getUserIdByUsername(idop);
				}
				int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (type2+"§"+idbook).split("§")));
				new Bdd().sendCmd(Reason.returnbook, (idu+"§"+idb).split("§"));					
				
			} else {
				int idu = -1;
				String choix = Reader.readBoolean("id username -exit", "Par le pseudo ou l'id ? (id/username)\nSi vous voulez annuler taper '-exit'\n");
				if (choix.equalsIgnoreCase("id")) {
					idu = Reader.readInt("Donnez-moi son ID\n");
				} else if (choix.equalsIgnoreCase("username")) {
					String pseudo = Reader.readString("Donnez-moi son pseudo\n");
					if (!pseudo.equalsIgnoreCase("-exit")) {
						idu = Integer.parseInt(new Bdd().sendCmd(Reason.searchuser, pseudo));
					}
				}
				
				choix = Reader.readBoolean("title author year title_author title_author_numedition desc -exit", "Par quel façon ? (title:author:year:title_author:title_author_numedition:desc)\nSi vous voulez annuler taper '-exit'\n");
				if (choix.equalsIgnoreCase("title")) {
					String title = Reader.readString("Donnez-moi son titre\n");
					if (!title.equalsIgnoreCase("-exit")) {
						int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (choix+"§"+title).split("§")));
						new Bdd().sendCmd(Reason.returnbook, (idu+"§"+idb).split("§"));
					}
				} else if (choix.equalsIgnoreCase("author")) {
					String auth = Reader.readString("Donnez-moi son auteur\n");
					if (!auth.equalsIgnoreCase("-exit")) {
						int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (choix+"§"+auth).split("§")));
						new Bdd().sendCmd(Reason.returnbook, (idu+"§"+idb).split("§"));
					}
				} else if (choix.equalsIgnoreCase("year")) {
					int year = Reader.readInt("Donnez-moi son année de parution\n");
					if (year!=-1) {
						int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (choix+"§"+year).split("§")));
						new Bdd().sendCmd(Reason.returnbook, (idu+"§"+idb).split("§"));
					}
				} else if (choix.equalsIgnoreCase("title_author")) {
					String title = Reader.readString("Donnez-moi son titre\n");
					String auth = Reader.readString("Donnez-moi son auteur\n");
					if (!auth.equalsIgnoreCase("-exit") && !title.equalsIgnoreCase("-exit")) {
						int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (choix+"§"+title+"§"+auth).split("§")));
						new Bdd().sendCmd(Reason.returnbook, (idu+"§"+idb).split("§"));
					}
				} else if (choix.equalsIgnoreCase("title_author_numedition")) {
					String title = Reader.readString("Donnez-moi son titre\n");
					String auth = Reader.readString("Donnez-moi son auteur\n");
					int numedit = Reader.readInt("Donnez-moi son numéro d'édition\n");
					if (!auth.equalsIgnoreCase("-exit") && !title.equalsIgnoreCase("-exit") && numedit!=-1) {
						int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (choix+"§"+title+"§"+auth+"§"+numedit).split("§")));
						new Bdd().sendCmd(Reason.returnbook, (idu+"§"+idb).split("§"));
					}
				} else if (choix.equalsIgnoreCase("desc")) {
					String desc = Reader.readString("Donnez-moi sa description\n");
					if (!desc.equalsIgnoreCase("-exit")) {
						int idb = Integer.parseInt(new Bdd().sendCmd(Reason.searchbook, (choix+"§"+desc).split("§")));
						new Bdd().sendCmd(Reason.returnbook, (idu+"§"+idb).split("§"));
					}
				}
			}
		}
		
	}
}























