import java.util.*;

/**
 * 
 * @author Luca Iezzi, Daniel Hrituc
 *
 */
public class Population implements Evolution {

    private final int suggestionvalue = 5;
    private List<Person<Individual.Type>> morList = new ArrayList<>();
    private List<Person<Individual.Type>> avvList = new ArrayList<>();
    private List<Person<Individual.Type>> pruList = new ArrayList<>();
    private List<Person<Individual.Type>> sprList = new ArrayList<>();
    private int mor, avv, pru, spr, a, b, c;
    private boolean abcSetted = false;

	/**
	 * Classe per gestire una coppia tipo-intero utile in vari contesti.
	 */
	class Pair {
		private Individual.Type type;
		private int suggestion;
		public Pair(Individual.Type t, int s){
			this.suggestion = s;
            this.type = t;
		}
	}

    /**
     * Classe per gestire una coppia persona-intero.
     */
	class PersonPair {
	    private Person<Individual.Type> person;
	    private int index;
	    public PersonPair(Person<Individual.Type> p, int i) {
	        this.person = p;
	        this.index = i;
        }
    }

    /**
     * Costruttore di una popolazione
     * @param mor numero di morigerati
     * @param avv numero di avventurieri
     * @param spr numero di spregiudicate
     * @param pru numero di prudenti
     */
	public Population(int mor, int avv, int spr, int pru) {
        this.mor = mor;
        this.avv = avv;
        this.pru = pru;
        this.spr = spr;
        for (int i = 0; i < mor; i++) {
            morList.add(new Person<>(Individual.Type.M));
        }
        for (int i = 0; i < avv; i++) {
            avvList.add(new Person<>(Individual.Type.A));
        }
        for (int i = 0; i < pru; i++) {
            pruList.add(new Person<>(Individual.Type.P));
        }
        for (int i = 0; i < spr; i++) {
            sprList.add(new Person<>(Individual.Type.S));
        }
    }

    /**
     * Metodo che calcola il valore del suggestion dato dai genitori.
     * @param p1 primo genitore.
     * @param p2 l'altro genitore.
     * @param childtype il tipo del figlio.
     * @param positive un flag che dice se il consiglio è positivo (vengono consigliati ai figli i tipi dei loro
     *                 genitori) o negativo (viene consigliato il tipo opposto).
     * @return se il figlio è di tipo M o S, un Pair con tipo il tipo consigliato dai genitori, e in suggestion il
     * valore che verrà sommato alla probabilità che il figlio ha di scegliere quel tipo. Altrimenti ritorna null
     * perchè i tipi A e P sono vincolati a una sola scelta di partner.
     */
	private Pair suggestion(Person<Individual.Type> p1, Person<Individual.Type> p2, Individual.Type childtype, boolean positive) {
        if (positive) {
            if (childtype.equals(Individual.Type.M)) {
                if (p1.sex == 0) {
                    return new Pair(p1.getType(),suggestionvalue);
                } else {
                    return new Pair(p2.getType(),suggestionvalue);
                }
            } else if (childtype.equals(Individual.Type.S)) {
                if (p1.sex == 1) {
                    return new Pair(p1.getType(),suggestionvalue);
                } else {
                    return new Pair(p2.getType(),suggestionvalue);
                }
            }
        } else {
            if (childtype.equals(Individual.Type.M)) {
                if (p1.getType().equals(Individual.Type.P)) {
                    return new Pair(Individual.Type.S,suggestionvalue);
                } else if (p1.getType().equals(Individual.Type.S)) {
                    return new Pair(Individual.Type.P,suggestionvalue);
                } else if (p2.getType().equals(Individual.Type.P)) {
                    return new Pair(Individual.Type.S,suggestionvalue);
                } else if (p2.getType().equals(Individual.Type.S)) {
                    return new Pair(Individual.Type.P,suggestionvalue);
                }
            } else if (childtype.equals(Individual.Type.S)) {
                if (p1.getType().equals(Individual.Type.M)) {
                    return new Pair(Individual.Type.A,suggestionvalue);
                } else if (p1.getType().equals(Individual.Type.A)) {
                    return new Pair(Individual.Type.M,suggestionvalue);
                } else if (p2.getType().equals(Individual.Type.M)) {
                    return new Pair(Individual.Type.A,suggestionvalue);
                } else if (p2.getType().equals(Individual.Type.A)) {
                    return new Pair(Individual.Type.M,suggestionvalue);
                }
            }
        }
        return null;
	}

	private List<PersonPair> remaining = new ArrayList<>();
    @Override
    public void evolve() {
	    checkPeopleToKill();
        int mor_n = get30Percent(mor);
        int avv_n = get30Percent(avv);
        int pru_n = get30Percent(pru);
        int spr_n = get30Percent(spr);
        List<PersonPair> mor_list = choosePeople(mor_n, morList);
        List<PersonPair> avv_list = choosePeople(avv_n, avvList);
        List<PersonPair> pru_list = choosePeople(pru_n, pruList);
        List<PersonPair> spr_list = choosePeople(spr_n, sprList);
        if (remaining.size() != 0) { // se ci sono persone rimaste dal turno precedente.
            for (PersonPair pp : remaining) {
                if (pp.person.getTTL() != 0) {
                    switch (pp.person.getType()) {
                        case M: {
                            mor_list.add(0, pp);
                            break;
                        }
                        case A: {
                            avv_list.add(0, pp);
                            break;
                        }
                        case S: {
                            spr_list.add(0, pp);
                            break;
                        }
                        case P: {
                            pru_list.add(0, pp);
                            break;
                        }
                    }
                }
            }
        }
        remaining.clear();
        List<List<PersonPair>> l = new ArrayList<>();
        l.add(mor_list); l.add(avv_list); l.add(pru_list); l.add(spr_list);
        int max = getMaxSize(l);
        Random r = new Random();
        int n = r.nextInt(4); // per vedere chi inizia a scegliere

        for (int i = 0; i < 4*max; i++) {
            if (l.get(n).size() != 0) {
                PersonPair p1 = l.get(n).get(0); //prendo la prima PersonPair nella lista del tipo preso a caso
                int coin = r.nextInt(100); //lancio moneta. se è < 50 scelgo un tipo, altrimenti sceglo un altro. uso un lancio 0-99 per gestire il caso in cui ho suggestion.
                switch (p1.person.getType()) {
                    case M: {
                        int prob_pru = 49;
                        int prob_spr = 50;
                        if (Objects.equals(p1.person.getSuggestedType(),(Individual.Type.P))) {
                            prob_pru += p1.person.getSuggestion();
                            prob_spr -= p1.person.getSuggestion();
                        } else {
                            prob_pru -= p1.person.getSuggestion();
                            prob_spr += p1.person.getSuggestion();
                        }
                        if (coin <= prob_pru && l.get(2).size() != 0) { //se c'è almeno una prudente
                            PersonPair p2 = l.get(2).get(0); //prendo la prudente
                            int coin2 = r.nextInt(2);
                            if (coin2 == 0) { //la prudente decide se starci o meno
                                createFamily(p1.person, p2.person, p1.index, p2.index);
                                p1.person.decreaseTTL();
                                p2.person.decreaseTTL();
                                mor_list.remove(0);
                                pru_list.remove(0);
                            } else {
                                p1.person.decreaseTTL();
                                remaining.add(p1);
                                mor_list.remove(0);
                            }
                        } else if (coin <= prob_pru && l.get(2).size() == 0) {
                            p1.person.decreaseTTL();
                            remaining.add(p1);
                            mor_list.remove(0);
                        } else if (coin >= prob_spr && l.get(3).size() != 0) { //se c'è almeno una spregiudicata
                            PersonPair p2 = l.get(3).get(0); //prendo la spregiudicata
                            if (p2.person.getSuggestion() == 0) {
                                int coin2 = r.nextInt(2);
                                if (coin2 == 0) { //la spregiudicata decide se starci o meno
                                    createFamily(p1.person, p2.person, p1.index, p2.index);
                                    p1.person.decreaseTTL();
                                    p2.person.decreaseTTL();
                                    mor_list.remove(0);
                                    spr_list.remove(0);
                                } else {
                                    p1.person.decreaseTTL();
                                    remaining.add(p1);
                                    mor_list.remove(0);
                                }
                            } else {
                                int coin2 = r.nextInt(100);
                                if (Objects.equals(p2.person.getSuggestedType(), (Individual.Type.M))) { //se il consiglio è per M
                                    if (coin2 <= 49+p2.person.getSuggestion()) { //la spregiudicata decide se starci o meno
                                        createFamily(p1.person, p2.person, p1.index, p2.index);
                                        p1.person.decreaseTTL();
                                        p2.person.decreaseTTL();
                                        mor_list.remove(0);
                                        spr_list.remove(0);
                                    } else {
                                        p1.person.decreaseTTL();
                                        remaining.add(p1);
                                        mor_list.remove(0);
                                    }
                                } else {
                                    if (coin2 >= 50+p2.person.getSuggestion()) { //la spregiudicata decide se starci o meno
                                        createFamily(p1.person, p2.person, p1.index, p2.index);
                                        p1.person.decreaseTTL();
                                        p2.person.decreaseTTL();
                                        mor_list.remove(0);
                                        spr_list.remove(0);
                                    } else {
                                        p1.person.decreaseTTL();
                                        remaining.add(p1);
                                        mor_list.remove(0);
                                    }
                                }
                            }
                        } else if (coin >= prob_spr && l.get(3).size() == 0) {
                            p1.person.decreaseTTL();
                            remaining.add(p1);
                            mor_list.remove(0);
                        }
                        break;
                    }
                    case S: {
                        int prob_mor = 49;
                        int prob_avv = 50;
                        if (Objects.equals(p1.person.getSuggestedType(), (Individual.Type.M))) {
                            prob_mor += p1.person.getSuggestion();
                            prob_avv -= p1.person.getSuggestion();
                        } else {
                            prob_mor -= p1.person.getSuggestion();
                            prob_avv += p1.person.getSuggestion();
                        }
                        if (coin <= prob_mor && l.get(0).size() != 0) { //se c'è almeno un morigerato
                            PersonPair p2 = l.get(0).get(0); //prendo il morigerato
                            if (p2.person.getSuggestion() == 0) {
                                int coin2 = r.nextInt(2);
                                if (coin2 == 0) { //il morigerato decide se starci o meno
                                    createFamily(p1.person, p2.person, p1.index, p2.index);
                                    p1.person.decreaseTTL();
                                    p2.person.decreaseTTL();
                                    spr_list.remove(0);
                                    mor_list.remove(0);
                                } else {
                                    p1.person.decreaseTTL();
                                    remaining.add(p1);
                                    spr_list.remove(0);
                                }
                            } else {
                                int coin2 = r.nextInt(100);
                                if (Objects.equals(p2.person.getSuggestedType(), (Individual.Type.S))) { //se il consiglio è per S
                                    if (coin2 <= 49+p2.person.getSuggestion()) { //il morigerato decide se starci o meno
                                        createFamily(p1.person, p2.person, p1.index, p2.index);
                                        p1.person.decreaseTTL();
                                        p2.person.decreaseTTL();
                                        spr_list.remove(0);
                                        mor_list.remove(0);
                                    } else {
                                        p1.person.decreaseTTL();
                                        remaining.add(p1);
                                        spr_list.remove(0);
                                    }
                                } else {
                                    if (coin2 >= 50+p2.person.getSuggestion()) { //il morigerato decide se starci o meno
                                        createFamily(p1.person, p2.person, p1.index, p2.index);
                                        p1.person.decreaseTTL();
                                        p2.person.decreaseTTL();
                                        spr_list.remove(0);
                                        mor_list.remove(0);
                                    } else {
                                        p1.person.decreaseTTL();
                                        remaining.add(p1);
                                        spr_list.remove(0);
                                    }
                                }
                            }
                        } else if (coin <= prob_mor && l.get(0).size() == 0) {
                            p1.person.decreaseTTL();
                            remaining.add(p1);
                        } else if (coin >= prob_avv && l.get(1).size() != 0) { //se c'è almeno un avventuriero
                            PersonPair p2 = l.get(1).get(0); //prendo l'avventuriero
                            if (p2.person.getSuggestion() == 0) {
                                int coin2 = r.nextInt(2);
                                if (coin2 == 0) { //l'avventuriero decide se starci o meno
                                    createFamily(p1.person, p2.person, p1.index, p2.index);
                                    p1.person.decreaseTTL();
                                    p2.person.decreaseTTL();
                                    spr_list.remove(0);
                                    avv_list.remove(0);
                                } else {
                                    p1.person.decreaseTTL();
                                    remaining.add(p1);
                                    spr_list.remove(0);
                                }
                            }
                        } else if (coin >= prob_avv && l.get(1).size() == 0) {
                            p1.person.decreaseTTL();
                            remaining.add(p1);
                            spr_list.remove(0);
                        }
                        break;
                    }
                    case P: {
                        if (coin <= 49 && l.get(0).size() != 0) { //se c'è almeno un morigerato
                            PersonPair p2 = l.get(0).get(0); //prendo il morigerato
                            if (p2.person.getSuggestion() == 0) {
                                int coin2 = r.nextInt(2);
                                if (coin2 == 0) { //il morigerato decide se starci o meno
                                    createFamily(p1.person, p2.person, p1.index, p2.index);
                                    p1.person.decreaseTTL();
                                    p2.person.decreaseTTL();
                                    pru_list.remove(0);
                                    mor_list.remove(0);
                                } else {
                                    p1.person.decreaseTTL();
                                    remaining.add(p1);
                                    pru_list.remove(0);
                                }
                            } else {
                                int coin2 = r.nextInt(100);
                                if (Objects.equals(p2.person.getSuggestedType(), (Individual.Type.P))) { //se il consiglio è per P
                                    if (coin2 <= 49+p2.person.getSuggestion()) { //il morigerato decide se starci o meno
                                        createFamily(p1.person, p2.person, p1.index, p2.index);
                                        p1.person.decreaseTTL();
                                        p2.person.decreaseTTL();
                                        pru_list.remove(0);
                                        mor_list.remove(0);
                                    } else {
                                        p1.person.decreaseTTL();
                                        remaining.add(p1);
                                        pru_list.remove(0);
                                    }
                                } else {
                                    if (coin2 >= 50+p2.person.getSuggestion()) { //il morigerato decide se starci o meno
                                        createFamily(p1.person, p2.person, p1.index, p2.index);
                                        p1.person.decreaseTTL();
                                        p2.person.decreaseTTL();
                                        pru_list.remove(0);
                                        mor_list.remove(0);
                                    } else {
                                        p1.person.decreaseTTL();
                                        remaining.add(p1);
                                        pru_list.remove(0);
                                    }
                                }
                            }
                        } else {
                            p1.person.decreaseTTL();
                            remaining.add(p1);
                            pru_list.remove(0);
                        }
                        break;
                    }
                    case A: {
                        if (coin <= 49 && l.get(3).size() != 0) { //se c'è almeno una spregiudicata
                            PersonPair p2 = l.get(3).get(0); //prendo la spregiudicata
                            if (p2.person.getSuggestion() == 0) {
                                int coin2 = r.nextInt(2);
                                if (coin2 == 0) { //la spregiudicata decide se starci o meno
                                    createFamily(p1.person, p2.person, p1.index, p2.index);
                                    p1.person.decreaseTTL();
                                    p2.person.decreaseTTL();
                                    avv_list.remove(0);
                                    spr_list.remove(0);
                                } else {
                                    p1.person.decreaseTTL();
                                    remaining.add(p1);
                                    avv_list.remove(0);
                                }
                            } else {
                                int coin2 = r.nextInt(100);
                                if (Objects.equals(p2.person.getSuggestedType(), (Individual.Type.A))) { //se il consiglio è per A
                                    if (coin2 <= 49+p2.person.getSuggestion()) { //la spregiudicata decide se starci o meno
                                        createFamily(p1.person, p2.person, p1.index, p2.index);
                                        p1.person.decreaseTTL();
                                        p2.person.decreaseTTL();
                                        avv_list.remove(0);
                                        spr_list.remove(0);

                                    } else {
                                        p1.person.decreaseTTL();
                                        remaining.add(p1);
                                        avv_list.remove(0);
                                    }
                                } else {
                                    if (coin2 >= 50+p2.person.getSuggestion()) { //la spregiudicata decide se starci o meno
                                        createFamily(p1.person, p2.person, p1.index, p2.index);
                                        p1.person.decreaseTTL();
                                        p2.person.decreaseTTL();
                                        avv_list.remove(0);
                                        spr_list.remove(0);
                                    } else {
                                        p1.person.decreaseTTL();
                                        remaining.add(p1);
                                        avv_list.remove(0);
                                    }
                                }
                            }
                        } else {
                            p1.person.decreaseTTL();
                            remaining.add(p1);
                            avv_list.remove(0);
                        }
                        break;
                    }
                }
            }
            n = (n+1)%4;
        }
    }

    /**
     * Chiamata prima di effettuare una evolve. Controlla che ci siano persone con TTL = 0 e le uccide.
     */
    private void checkPeopleToKill() {
        for (int i = 0; i < morList.size();) {
            if (morList.get(i).getTTL() == 0) {
                killPerson(morList.get(i),i);
            } else {
                i++;
            }
        }
        for (int i = 0; i < avvList.size();) {
            if (avvList.get(i).getTTL() == 0) {
                killPerson(avvList.get(i),i);
            } else {
                i++;
            }
        }
        for (int i = 0; i < pruList.size();) {
            if (pruList.get(i).getTTL() == 0) {
                killPerson(pruList.get(i),i);
            } else {
                i++;
            }
        }
        for (int i = 0; i < sprList.size();) {
            if (sprList.get(i).getTTL() == 0) {
                killPerson(sprList.get(i),i);
            } else {
                i++;
            }
        }
    }

    private int getMaxSize(List<List<PersonPair>> l) {
	    int n = 0;
        for (List<PersonPair> l_pp : l) {
            if (n < l_pp.size()) n = l_pp.size();
        }
        return n;
    }

    /**
     * Metodo che seleziona a caso un certo numero di persone de una lista.
     * @param quantity il numero di persone da selezionare.
     * @param list la lista di persone dalla quale selezionare.
     * @return la lista di PersonPair relativa alle persone selezionate.
     */
    private List<PersonPair> choosePeople(int quantity, List<Person<Individual.Type>> list) {
        List<PersonPair> l = new ArrayList<>();
	    Random r = new Random();
        Set<Integer> set = new HashSet<>();
        Set<Integer> avoidLoop = new HashSet<>();
        boolean looped = false;
        int n;
        for (int i = 0; i < quantity; i++) {
            n = r.nextInt(quantity);
            while (set.contains(n) || list.get(n).isParent || list.get(n).getTTL() != list.get(n).defaultTTL) {
                avoidLoop.add(n);
                if (avoidLoop.size() == quantity) {
                    looped = true;
                    break;
                }
                n = r.nextInt(quantity);
            }
            set.add(n);
            if (!looped) l.add(new PersonPair(list.get(n),n));
            else looped = false;
        }
        return l;
    }

    /**
     * Metodo per ottenere il 30% di un numero.
     * @param i il numero.
     * @return il 30% di i.
     */
    private int get30Percent(int i) {
        double d = ((double)i*30.00/100.00);
        if (d < 1) return i;
        return (int)d;
    }

    @Override
	public boolean createFamily(Person<Individual.Type> p1, Person<Individual.Type> p2, int i1, int i2) {
		int n = childrenNumber(p1.getType(),p2.getType());
        if (n == 0) return false;
        p1.isParent = true;
        p2.isParent = true;
        int[] payoff = new int[2];
        boolean killed = false;
        List<Person<Individual.Type>> children = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int[] tmp = Individual.payoff(p1, p2, a, b, c);
            payoff[0] += tmp[0]; //payoff della donna
            payoff[1] += tmp[1]; //payoff dell'uomo
            int[] child = typeOfChildren(p1.getType(), p2.getType());
            if (payoff[0] < 0) { //se muore la madre
                killPerson(p1,i1);
                killed = true;
            }
            if (payoff[1] < 0) { //se muore il padre
                killPerson(p2,i2);
                killed = true;
            }
            if (child[1] == 0) children.add(new Person<>(Individual.Type.P));  //i numeri corrispondono ai tipi. vedi metodo typeOfChildren
            else if (child[1] == 1) children.add(new Person<>(Individual.Type.M));
            else if (child[1] == 2) children.add(new Person<>(Individual.Type.S));
            else children.add(new Person<>(Individual.Type.A));
            if (killed) break;  //se un genitore muore si interrompe la creazione dei figli.
        }
        if (killed) {
            for (Person<Individual.Type> c : children) {
                Pair sugg = suggestion(p1,p2,c.getType(),false);
                if (sugg == null) {
                    if (c.getType().equals(Individual.Type.A)) {
                        avvList.add(new Person<>(Individual.Type.A));
                        avv++;
                    } else {
                        pruList.add(new Person<>(Individual.Type.P));
                        pru++;
                    }
                } else {
                    if (c.getType().equals(Individual.Type.M)) {
                        Person<Individual.Type> p = new Person<>(Individual.Type.M);
                        p.setSuggestedType(sugg.type); p.setSuggestion(sugg.suggestion);
                        morList.add(p);
                        mor++;
                    } else if (c.getType().equals(Individual.Type.S)) {
                        Person<Individual.Type> p = new Person<>(Individual.Type.S);
                        p.setSuggestedType(sugg.type); p.setSuggestion(sugg.suggestion);
                        sprList.add(p);
                        spr++;
                    }
                }
            }
        } else {
            for (Person<Individual.Type> c : children) {
                Pair sugg = suggestion(p1,p2,c.getType(),true);
                if (sugg == null) {
                    if (c.getType().equals(Individual.Type.A)) {
                        avvList.add(new Person<>(Individual.Type.A));
                        avv++;
                    } else {
                        pruList.add(new Person<>(Individual.Type.P));
                        pru++;
                    }
                } else {
                    if (c.getType().equals(Individual.Type.M)) {
                        Person<Individual.Type> p = new Person<>(Individual.Type.M);
                        p.setSuggestedType(sugg.type); p.setSuggestion(sugg.suggestion);
                        morList.add(p);
                        mor++;
                    } else if (c.getType().equals(Individual.Type.S)) {
                        Person<Individual.Type> p = new Person<>(Individual.Type.S);
                        p.setSuggestedType(sugg.type); p.setSuggestion(sugg.suggestion);
                        sprList.add(p);
                        spr++;
                    }
                }
            }
        }
        return true;
    }

	@Override
	public boolean killPerson(Person<Individual.Type> p, int i) {
		switch(p.getType()){
            case P:
                pruList.remove(i);
                pru--;
                break;
            case A:
                avvList.remove(i);
                avv--;
                break;
            case M:
                morList.remove(i);
                mor--;
                break;
            case S:
                sprList.remove(i);
                spr--;
                break;
        }
        for (PersonPair pers : remaining) {
            if (pers.person.getType().equals(p.getType()) && pers.index > i) {
                pers.index--;
            }
		}
		return true;
	}

	@Override
	public void setValues(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
        abcSetted = true;
	}

	@Override
	public boolean valuesIsSet() {
        return abcSetted;
	}

	@Override
	public boolean isNextTo(int[] first, int[] other) {
        for (int i = 0; i < 4; i++) {
            if (Math.abs(first[i]-other[i]) > 5) return false;
        }
        return true;
    }

	@Override
	public boolean checkForStability(List<int[]> l) {
        for (int i = 1; i < l.size(); i++) {
            if (!isNextTo(l.get(0),l.get(i))) return false;
        }
        return true;
    }

	@Override
	public double getPercentage(Individual.Type t) throws IllegalArgumentException {
        int tot = mor+avv+spr+pru;
        switch (t) {
            case A: return Math.floor(((double)avv/(double)tot)*100 * 100) / 100;
            case M: return Math.floor(((double)mor/(double)tot)*100 * 100) / 100;
            case P: return Math.floor(((double)pru/(double)tot)*100 * 100) / 100;
            case S: return Math.floor(((double)spr/(double)tot)*100 * 100) / 100;
            default: return 0;
        }
	}

    /**
     * La funzione calcola il numero di figli di una coppia. Il calcolo dovrebbe seguire delle probabilità razionali,
     * ovvero la probabilità di avere un figlio è più alta di quella di averne 2 e così via.
     * Per esempio la funzione [ 1/(ln(x+1.34)) - 0.47] * 100 (si prende la parte intera inferiore) offre percentuali
     * interessanti:
     *      x = 1 ---> 70%
     *      x = 2 ---> 35%
     *      x = 3 ---> 21%
     *      x = 4 ---> 12%
     *      x = 5 ---> 7%
     *      x = 6 ---> 3%
     * Per valori più alti la percentuale è 0
     *
     * Funzione inversa: e^(100/y+47)-1.34
     * Scelgo y a caso tra gli interi [0,100] e arrotondo con Math.floor(y) il risultato ottenendo il numero di figli.
     */
    private int childrenNumber(Individual.Type parent1, Individual.Type parent2) {
        if ((parent1.equals(Individual.Type.A) && parent2.equals(Individual.Type.P)) || (parent1.equals(Individual.Type.P) && parent2.equals(Individual.Type.A)) ) {
            return 0;
        }
        if (parent1.equals(Individual.Type.A) || parent2.equals(Individual.Type.A)) {
            return 1;
        }
        int y = new Random().nextInt(101);
        return (int)Math.floor(Math.exp(100.00/((double)y+47.00))-1.34);
    }

    /**
     * Metodo che stabilisce il sesso e il tipo di un figlio. Per il sesso abbiamo un 50% di probabilità per entrambe
     * le possibilità. Per il tipo, ho applicato questa regola:
     *      - Mamma P e papà M ---> 75% possibilità che sia un P o un M; 25% possibilità che sia S o A
     *      - Mamma S e papà M ---> 50% possibilità che sia un P o un M; 50% possibilità che sia S o A
     *      - Mamma S e papà A ---> 25% possibilità che sia un P o un M; 75% possibilità che sia S o A
     * @param parent1 un genitore.
     * @param parent2 l'altro genitore.
     * @throws IllegalArgumentException se si fornisce come genitori la coppia AP o PA, oppure se si forniscono input
     * che non corrispondono a nessun tipo.
     * @return un array di interi in cui la prima posizione è un valore per il sesso (0 è femmina, 1 maschio), mentre
     * la seconda posizione è un valore per il tipo (0 = P, 1 = M, 2 = S, 3 = A)
     */
    private int[] typeOfChildren(Individual.Type parent1, Individual.Type parent2) throws IllegalArgumentException {
        /*
        if (parent1.equals(Individual.Type.P) && parent2.equals(Individual.Type.A) || (parent1.equals(Individual.Type.A) && parent2.equals(Individual.Type.P))) {
            throw new IllegalArgumentException();
        }
        if ((!parent1.equals(Individual.Type.M)) || !parent1.equals(Individual.Type.A) || !parent1.equals(Individual.Type.S) || !parent1.equals(Individual.Type.P)) {
            throw new IllegalArgumentException();
        }
        if ((!parent2.equals(Individual.Type.M)) || !parent2.equals(Individual.Type.A) || !parent2.equals(Individual.Type.S) || !parent2.equals(Individual.Type.P)) {
            throw new IllegalArgumentException();
        }
        */
        int[] figlio = new int[2];
        figlio[0] = new Random().nextInt(2);
        if ((parent1.equals(Individual.Type.M) && parent2.equals(Individual.Type.P)) || parent1.equals(Individual.Type.P) && parent2.equals(Individual.Type.M)) {
            int n = new Random().nextInt(101);
            if (n <= 75) {
                figlio[1] = (figlio[0] == 0 ? 0 : 1);
            } else {
                figlio[1] = (figlio[0] == 0 ? 2 : 3);
            }
        } else if ((parent1.equals(Individual.Type.M) && parent2.equals(Individual.Type.S)) || (parent1.equals(Individual.Type.S) && parent2.equals(Individual.Type.M))) {
            int n = new Random().nextInt(2);
            if (n <= 1) {
                figlio[1] = (figlio[0] == 0 ? 0 : 1);
            } else {
                figlio[1] = (figlio[0] == 0 ? 2 : 3);
            }
        } else {
            int n = new Random().nextInt(101);
            if (n <= 25) {
                figlio[1] = (figlio[0] == 0 ? 0 : 1);
            } else {
                figlio[1] = (figlio[0] == 0 ? 2 : 3);
            }
        }
        return figlio;
    }


    /* ****************TESTING METODES******************** */

    public int[] currentPopulation() {
        int[] n = new int[4];
        n[0] = mor; n[1] = avv; n[2] = pru; n[3] = spr;
        return n;
    }
}
