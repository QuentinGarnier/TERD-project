package graphics;

import entity.AbstractEntity;
import entity.EntityState;
import entity.EntityType;
import entity.Player;
import graphics.elements.Move;
import graphics.map.Theme;
import graphics.map.WorldMap;
import graphics.window.GameWindow;
import items.AbstractItem;
import items.ItemTrap;
import items.collectables.ConsumableTypes;
import items.collectables.EquipmentTypes;
import items.collectables.ItemEquip;

public enum Language {
    EN, FR, IT, AR;

    private static String lang(String en, String fr, String it, String ar) {
        Language l = GameWindow.language();
        return l == IT ? it : (l == FR ? fr : (l == EN ? en : ar));
    }


    // ===== Start menu buttons ===== //
    public static String newGame() {
        return lang("New Game", "Nouvelle Partie", "Nuova Partita", "جزء جديد");
    }
    public static String options() {
        return lang("Options", "Options", "Opzioni", "خيارات");
    }
    public static String help() {
        return lang("Help", "Aide", "Aiuto", "يساعد");
    }
    public static String ranking() {
        return lang("Ranking", "Classement", "Classifica", "تصنيف");
    }
    public static String exitGame() {
        return lang("Exit Game", "Quitter le Jeu", "Esci dal Gioco", "اترك اللعبة");
    }
    public static String back() {
        return lang("Back", "Retour", "Indietro", "يعود");
    }
    public static String startTheQuest() {
        return lang("Start the Quest", "Commencer la Quête", "Inizia la Missione", "ابدأ المهمة");
    }
    public static String validate() {
        return lang("Validate", "Valider", "Applica", "تحقق");
    }



    // ===== Chara selection menu ===== //
    public static String chooseYourSpeciality() {
        return lang("Choose your speciality", "Choisissez votre spécialité", "Scegli la tua specialità", "اختر تخصصك");
    }
    public static String warriorCL() {
        return lang("WARRIOR", "GUERRIER", "GUERRIERO", "محارب"); /* CL is for 'CapsLock'  */
    }
    public static String archerCL() {
        return lang("ARCHER", "ARCHER", "ARCIERE", "آرتشر"); /* CL is for 'CapsLock'  */
    }
    public static String mageCL() {
        return lang("MAGE", "MAGE", "MAGO", "بركه"); /* CL is for 'CapsLock'  */
    }
    public static String warriorDescription() {
        String body1 = lang("The warrior deals great melee damage and has a large amount of HP.",
                "Le guerrier inflige de puissants dommages de mêlée et possède une large quantité de PV.",
                "Il guerriero infligge grandi danni di mischia e possiede una grossa quantità di HP.",
                "يلحق المحارب ضررًا قويًا بالاشتباك ويتمتع بقدر كبير من الصحة.");
        String body2 = lang("He becomes Enraged when his HP is low. In counterpart, he loses 1 Hunger Point for each attack.",
                "Il devient Enragé quand ses PV sont bas. En contrepartie, il perd 1 Point de Faim à chaque attaque.",
                "Si arrabbia quando i suoi HP sono bassi. Tuttavia, perde 1 Punto Fame a ogni attacco",
                "يصبح غاضبًا عندما تكون نقاط صحته منخفضة. في المقابل ، يخسر نقطة جوع واحدة لكل هجوم.");
        return "<html><p style=\"text-align: center;\">" + body1 + "<br />" + body2 + "</p></html>";
    }
    public static String archerDescription() {
        String body1 = lang("The archer deals good distance damage with his very long range but has few HP.",
                "L'archère inflige de bons dommages à distance avec une grande portée mais possède peu de PV.",
                "L'arciere infligge danni a distanza grazie alla sua grande portata, ma possiede pochi HP.",
                "يتعامل رامي السهام مع ضرر جيد بعيد المدى ولكنه يتمتع بصحة قليلة.");
        String body2 = lang("Each attack has a chance to deal more damage, inflict an effect... or miss the target.",
                "Chaque attaque a une chance d'infliger plus de dégâts, d'empoisonner l'adversaire... ou de rater la cible.",
                "Ogni attacco ha la probabilità d'infliggere più danni, di avvelenare l'avversario... o di mancare il bersaglio.",
                "كل هجوم لديه فرصة لإحداث المزيد من الضرر ، وتسميم الخصم ... أو تفويت الهدف.");
        return "<html><p style=\"text-align: center;\">" + body1 + "<br />" + body2 + "</p></html>";
    }
    public static String mageDescription() {
        String body1 = lang("The mage deals moderate area damage in a medium range.",
                "Le mage inflige des dégâts de zone modérés avec une portée moyenne.",
                "Il mago infligge dei danni a zona moderati con un raggio d'attacco medio.",
                "يتسبب السحرة في ضرر معتدل في مدى متوسط.");
        String body2 = lang("His power lies in his ability to burn, freeze or paralyse his opponents and heal himself slightly with each attack.",
                "Sa force réside dans sa capacité à brûler, geler ou paralyser ses adversaires et se soigner légèrement à chaque attaque.",
                "La sua forza è la capacità di bruciare, gelare o paralizzare i suoi avversari e curarsi leggermente a ogni attacco.",
                "تكمن قوتها في قدرتها على حرق أو تجميد أو شل المعارضين وشفاء نفسها برفق مع كل هجوم.");
        return "<html><p style=\"text-align: center;\">" + body1 + "<br />" + body2 + "</p></html>";
    }
    public static String enterYourName() {
        return lang("Enter your name:", "Entrez votre nom :", "Inserisci il tuo nome:", "أدخل أسمك:");
    }



    // ===== Options menu ===== //
    public static String selectTheLanguage() {
        return lang("Select language", "Sélectionnez la langue", "Seleziona la lingua", "اختار اللغة");
    }
    public static String gameSound() {
        return lang("Sounds", "Sons", "Suoni", "صوت اللعبة");
    }
    public static String keyBindings() {
        return lang("Keys", "Touches", "Tasti", "مفاتيح");
    }
    public static String enterKey() {
        return lang("Press a key", "Appuyez sur une touche", "Premi un tasto", "اضغط على زر");
    }
    public static String cancel() {
        return lang("Cancel", "Annuler", "Annullare", "لالغاء");
    }
    public static String reset() {
        return lang("Reset", "Restaurer", "Reset", "يعيد");
    }
    public static String confirm() {
        return lang("Confirm", "Confirmer", "Confermare", "أكد");
    }
    public static String resolution() {
        return lang("Resolution", "Résolution", "Risoluzione", "القرار");
    }
    public static String chooseTheDifficulty() {
        return lang("Choose the difficulty", "Choisissez la difficulté", "Scegli la difficoltà", "اختر الصعوبة");
    }
    public static String easy() {
        return lang("Easy", "Facile", "Facile", "سهل");
    }
    public static String medium() {
        return lang("Medium", "Moyen", "Medio", "طريق");
    }
    public static String hard() {
        return lang("Hard", "Difficile", "Difficile", "صعب");
    }
    public static String nightmare() {
        return lang("Nightmare", "Cauchemar", "Incubo", "كابوس");
    }
    public static String endless() {
        return lang("Endless", "Sans fin", "Infinito", "بدون نهاية");
    }



    // ===== Stats panel (in game) ===== //
    public static String heroCP() {
        return lang("HERO", "HÉROS", "EROE","بطل");
    }
    public static String level() {
        return lang("Level", "Niveau ", "Livello","مستوى");
    }
    public static String hp() {
        return lang("HP", "PV", "HP","الحياة");
    }
    public static String hunger() {
        return lang("Hunger", "Faim ", "Fame","جوع");
    }
    public static String attack() {
        return lang("Attack", "Attaque ", "Attacco", "هجوم");
    }
    public static String defense() {
        return lang("Defense", "Défense", "Difesa", "دفاع");
    }
    public static String range() {
        return lang("Range", "Portée ", "Portata", "مدى");
    }
    public static String money() {
        return lang("Money","Monnaie ", "Moneta","فضة");
    }
    public static String stage() {
        return lang("Stage", "Étage ", "Piano", "طابق");
    }



    // ===== Menu/pause panel (in game) ===== //
    public static String sound(boolean b) {
        return lang((b ? "Activate" : "Stop") + " the sound",
               (b ? "Activer" : "Désactiver") + " le son",
                (b ? "Attivare" : "Disattivare") + "l'audio",
                (b ? "تفعيل" : "تعطيل") + "الصوت");
    }
    public static String resume() {
        return lang("Resume", "Reprendre", "Riprendere", "الاستئناف");
    }
    public static String menu() {
        return lang("Main menu", "Menu principal", "Menu principale", "القائمة");
    }



    // ===== Logs ===== //
    public static String logGainMoney(int value) {
        return lang("You have found: " + value + " coin" + (value>1? "s!": "!"),
                "Vous avez trouvé : " + value + " pièce" + (value>1? "s !": " !"),
                "Hai trovato: " + value + " monet" + (value>1? "e!": "a!"),
                "انت وجدت : " + value  + (value>1? "عملات معدنية" : "عملة "));
    }
    public static String logGainItem(AbstractItem i) {
        return lang("You have found: " + i + "!",
                "Vous avez trouvé : " + i + " !",
                "Hai trovato: " + i + "!",
                "انت وجدت : " + i + " !")
                + (i instanceof ItemEquip ? " [" + logEquipmentRarity((ItemEquip)i) + "]" : "");
    }

    public static String logEquipmentRarity(ItemEquip ie) {
        return switch (ie.getEquipmentType().getRarity()) {
            case COMMON -> lang("Common", "Commun", "Comune", "مشترك");
            case RARE -> lang("Rare", "Rare", "Raro", "نادر");
            case EPIC -> lang("Epic", "Épique", "Epico", "ملحمي");
            case LEGENDARY -> lang("Legendary", "Légendaire", "Leggendario", "أسطوري");

        };
    }
    public static String logLowerFloor() {
        return !WorldMap.getInstanceWorld().lastLevel()?
                lang("You enter the lower floor...",
                "Vous pénétrez à l'étage inférieur...",
                "Si entra nel piano inferiore...",
                "تدخل الطابق السفلي ...") :
                lang("Darkness is coming...", "Les ténèbres se rapprochent...",
                        "Le tenebre si avvicinano...", "الظلام يقترب ...");
    }
    public static String logNothingHappens(AbstractEntity e) {
        return lang(e + " is Immune.", e + " est Immunisé.", e + " è Immune.", " محصن." + e);
    }
    public static String logDealDamage(AbstractEntity entity1, AbstractEntity entity2) {
        boolean b = entity2 instanceof Player;
        int dmg = (Math.max(0, entity1.getAttack() - (b ? Player.getInstancePlayer().getDefense() : 0)));
        return lang(entity1 + " deals " + dmg + " damage to " + entity2 + "." + (dmg != entity1.getAttack() ? " (Absorbed: " + (entity1.getAttack() - dmg) + ")" : ""),
                entity1 + " inflige " + dmg + " dégâts à " + entity2 + "." + (dmg != entity1.getAttack() ? " (Absorbé : " + (entity1.getAttack() - dmg) + ")" : ""),
                entity1 + " infligge " + dmg + " danni a " + entity2 + "." + (dmg != entity1.getAttack() ? " (Assorbito: " + (entity1.getAttack() - dmg) + ")" : ""),
                entity1 + " يلحق " + dmg + " ضرر لأ " + entity2 + "." + (dmg != entity1.getAttack() ? "(" + (entity1.getAttack() - dmg) + " (يمتص " : ""));
    }

    public static String logAreaDamage(AbstractEntity monster) {
        int dmg = (int) (Player.getInstancePlayer().getAttack() * 0.20);
        return lang("A" + (monster.getEntityType() == EntityType.MONSTER_ORC ? "n " : " ")  + monster + " suffers the Mage's area effect. [-" + dmg + " HP]",
                "Un" + (monster.getEntityType() == EntityType.MONSTER_SPIDER ? "e " : " ") + monster + " subit l'effet de zone du Mage. [-" + dmg + " PV]",
                "Un " + monster + " subisce l'effetto a zona del Mago [-" + dmg + " HP]",
                "يعان" + monster + "من تأثير منطقة الساحر [-" + dmg + "HP]");
    }

    public static String logCriticalHit(AbstractEntity entity1, AbstractEntity entity2, int atk) {
        return lang(entity1 + " deals " + atk + " damage to " + entity2 + " (critical hit)!",
                entity1 + " inflige " + atk + " de dégât à " + entity2 + " (coup critique) !",
                entity1 + " infligge " + atk + " danni a " + entity2 + "(colpo critico)!",
                entity1 + " يلحق " + atk + " ضرر لأ " + entity2 + "((ضربة حاسمة) !");
    }
    public static String logMissedTarget() {
        return lang("Missed target...", "Cible manquée...", "Bersaglio mancato...","هدف مفقود ...");
    }
    public static String logModifyHunger(int x) {
        return lang("You " + (x >= 0 ? "gain " + x : "lose " + (-x)) + " Hunger Point" + (x > 1 || x < -1 ? "s" : "") + ".",
                "Vous " + (x >= 0 ? "gagnez " + x : "perdez " + (-x)) + " Point" + (x > 1 || x < -1 ? "s" : "") + " de Faim.",
                (x >= 0 ? "Guadagni " + x : "Perdi " + (-x)) + " Punt" + (x > 1 || x < -1 ? "i" : "o") + " Fame.",
                "أنتم " + (x >= 0 ? "فوز " + x : "تخسر " + (-x)) + "لا جوع");
    }
    public static String logStarving() {
        return lang("You are starving...", "Vous êtes affamé...", "Stai morendo di fame...", "أنت تتضور جوعا...");
    }
    public static String logHeroDeath(boolean deathByHunger) {
        if(deathByHunger) return lang("HERO DIED OF HUNGER", "LE HÉROS EST MORT DE FAIM", "L'EROE È MORTO DI FAME","البطل مات من الجوع");
        return lang("HERO IS DEAD", "LE HÉROS EST MORT", "L'EROE È MORTO","البطل ميت");
    }
    public static String logHeroVictory() {
        return lang("You defeated the Evil!", "Vous avez vaincu le Mal !", "Hai sconfitto il Male!", "لقد انتصرت على الشر!");
    }
    public static String logCantDropItem() {
        return lang("You can't drop the item here.", "Vous ne pouvez pas jeter l'objet ici.", "Non puoi lasciare l'oggetto qui.","لا يمكنك رمي العنصر هنا.");
    }
    public static String logNotEnoughMoney() {
        return lang("Not enough money.", "Pas assez d'argent.", "Denaro insufficiente.","مال غير كاف.");
    }
    public static String logInventoryFull() {
        return lang("The inventory is full!", "L'inventaire est plein !", "L'inventario è pieno!","المخزون ممتلئ!");
    }

    public static String merketTitle() {
        return lang("Merchant's market", "La boutique du marchand", "Il negozio del commerciante", "محل التاجر");
    }

    public static String logBuyOrSell(boolean isBuy, boolean verbalBase) {
        return verbalBase ? (isBuy ? lang("Buy", "Acheter", "Comprare", "يشترى") : lang("Sell", "Vendre", "Vendere", "باع")) :
                (isBuy ? lang("bought!", "acheté(e) !", "acquistato!", "تم شراؤها!") : lang("resold!", "revendu(e) !", "rivenduto!", "إعادة بيعها!"));
    }

    public static String logMoney() {
        return lang("coins", "pièces", "monete", "عملات معدنية");
    }

    public static String confirmBuy() {
        return lang("Are you sure you want to purchase the item?", "Êtes-vous sûr de vouloir acheter l'item ?", "Sei sicuro di voler acquistare l'articolo?", "هل أنت متأكد أنك تريد شراء العنصر؟");
    }
    public static String confirmDialog(boolean isBuy, boolean isTitle) {
        return isTitle ? (isBuy ? lang("Confirmation of purchase", "Confirmation d'achat", "Conferma di acquisto","تأكيد الشراء") : lang("Confirmation of sale", "Confirmation de vente", "Conferma di vendita","تأكيد البيع")) :
                (isBuy ? lang("Are you sure you want to buy an item that you cannot equip?", "Voulez-vous vraiment acheter un item dont vous ne pourrez vous équiper ?", "Sei sicuro di voler acquistare un oggetto che non puoi equipaggiare?","هل أنت متأكد أنك تريد شراء عنصر لا يمكنك تجهيزه؟") : lang("Are you sure you want to sell the item?", "Voulez-vous vraiment vendre l'objet ?", "Sei sicuro di voler vendere l'articolo?","هل أنت متأكد أنك تريد بيع العنصر؟"));
    }
    public static String logLevelUp() {
        return lang(">>> LEVEL UP +1! <<<", ">>> NIVEAU +1! <<<", ">>> LIVELLO +1! <<<",">>> المستوى +1! <<<");
    }
    public static String logTrap(ItemTrap.Trap trap) {
        return switch (trap) {
            case BURNING -> lang("You stepped on a burning trap!", "Vous avez marché sur un piège brûlant !", "Hai calpestato una trappola infiammata!","لقد داسوا على مصيدة ساخنة!");
            case FREEZING -> lang("A magic trap freezes you!", "Un piège magique vous gèle !", "Una trappola magica ti congela!","مفاجأة شديدة تجمدك!");
            case POISONED -> lang("A poisoned dart!", "Un dard empoisonné !", "Un dardo avvelenato!","فخ مسموم!");
            case TELEPORT -> lang("You got caught by a teleporter trap!", "Un piège téléporteur !", "Una trappola ti teletrasporta!","فخ الناقل الآني!");
            case BOMB -> lang("A bomb was planted here! [-15 HP]", "Une bombe était enterrée là ! [-15 PV]", "Una bomba è stata piazzata qui! [-15 HP]","هناك دفنت قنبلة! [-15 حياة]");
        };
    }
    public static String logDie() {
        return lang("die", "meure ", "muore","موت");
    }
    public static String logFood() {
        return lang("Food", "Nourriture", "Cibo","طعام");
    }
    public static String logEffect(EntityState es) {
        String e = switch (es) {
            case BURNT -> lang("Burning", "de brûlure", "bruciante","احتراق");
            case FROZEN -> lang("Frozing", "de gelo", "congelante","الصقيع");
            case HEALED -> lang("Healing", "de guérison", "curativo","شفاء");
            case INVULNERABLE -> lang("Invulnerability", "d'invulnérabilité", "invulnerabilità","مناعة");
            case PARALYSED -> lang("Paralysis", "de paralysation", "paralisi","شلل");
            case POISONED -> lang("Poisoning", "d'empoisonnement", "avvelenamento","تسمم");
            case ENRAGED -> lang("Enraged", "d'enragement", "di rabbia","غاضب");
            default -> "";
        };
        return lang(e + " effect", "Effet " + e, "Effetto " + e, "تأثير" + e);
    }
    public static String logInventory() {
        return lang("Inventory", "Inventaire", "Inventario","المخزون");
    }
    public static String logEquipped() {
        return lang("equipped", "équipé(e)", "in uso","مجهز");
    }
    public static String logRejected() {
        return lang("rejected", "rejeté", "rifiutato", "مرفوض");
    }
    public static String logConsumed() {
        return lang("consumed", "consommé(e)", "consumato","مستهلك");
    }
    private static String logIsParalysedEffect(EntityType entityType) {
        Language l = GameWindow.language();
        return switch (entityType) {
            case HERO_WARRIOR -> " [-20% " + (l.equals(FR) ? attack().replaceFirst(".$","") : attack()) + "]";
            case HERO_ARCHER -> " [-2 " + range() + "]";
            case HERO_MAGE -> " [" + lang("Magic effect of attack neutralized.", "Effet magique de l'attaque neutralisé." ,"Effetto magico dell'attacco neutralizzato.","تحييد التأثير السحري للهجوم.") + "]";
            default -> "";
        };
    }
    public static String logEffect(AbstractEntity entity) {
        Language l = GameWindow.language();
        if (entity.getState() == EntityState.ENRAGED) {
            return lang("Rage makes ", "La rage rend ", "La rabbia rende ","يصنع داء الكلب") + entity
                    + lang(" stronger! [+10 ", " plus fort ! [+10 ", " più forte! [+10 ","اقوى ! [+10") + (l.equals(FR) ? attack().replaceFirst(".$","") : attack()) + "]";
        }
        return translate(entity.entityType) + switch (entity.getState()) {
            case BURNT -> lang(" is burning!", " brûle !", " sta bruciando!","يحرق!");
            case FROZEN -> lang(" is frozen!", " est gelé !", " è congelato!","مجمد!");
            case HEALED -> lang(" is healed!", " est soigné !", " è guarito!","أنيق!");
            case INVULNERABLE -> lang(" is invulnerable!", " est invulnérable !", " è invulnerabile!","هو منيع!");
            case PARALYSED -> lang(" is paralysed!", " est paralysé !", " è paralizzato!","مشلول!") + logIsParalysedEffect(entity.entityType);
            case POISONED -> lang(" is poisoned!", " est empoisonné !", " è avvelenato!","تسمم!");
            default -> "";
        };
    }
    public static String logBurnEffect(int amount) {
        return lang("The burn inflicts you " + amount + " damage.",
                "La brûlure vous inflige " + amount + " dégâts.",
                "La bruciatura ti infligge " + amount + " danni.",
                "يصيبك الحرق" + amount + " تلف.");
    }
    public static String logYouAreFrozen() {
        return lang("The gel keeps you from moving.",
                "Le gel vous empêche de bouger.",
                "Il gelo ti impedisce di muoverti.",
                "يمنعك الجل من الحركة.");
    }
    public static String logPoisonEffect(int amount) {
        return lang("You are suffering from poisoning. [-" + amount + " HP, -1 Hunger]",
                "Vous souffrez du poison. [-" + amount + " PV, -1 Faim]",
                "Soffri di avvelenamento. [-" + amount + " HP, -1 Fame]",
                "أنت تعاني من السم. [-" + amount + "الحياة ، -1 الجوع]");
    }
    public static String logHealEffect(int amount) {
        return lang("You are healed of " + amount + " HP.",
                "Vous êtes soigné de " + amount + " PV.",
                "Sei guarito di " + amount + " HP.",
                "أنت مهتم" + amount + "الحياة.");
    }
    public static String logTeleportBossRoom() {
        return lang("The surrounding dark magic prevents you from teleporting...",
                "La magie noire environnante vous empêche de vous téléporter...",
                "La magia nera que ti circonda ti impedisce il teletrasporto...",
                "يمنعك السحر المظلم المحيط من النقل الآني ...");
    }
    public static String logDragonExplo1() {
        return lang("Throwing the explosion in a room would have been more judicious...",
                "Lancer l'explosion dans une salle aurait été plus judicieux...",
                "Lanciare l'esplosione in una stanza sarebbe stato più giudizioso...",
                "كان من الممكن أن يكون إلقاء الانفجار في غرفة أكثر حكمة ...");
    }
    public static String logDragonExplo2() {
        return lang("The explosion frightened the Merchant...",
                "L'explosion a fait sursauter le Marchand...",
                "L'esplosione ha spaventato il Mercante...",
                "الانفجار أرعب التاجر ...");
    }
    public static String logDragonExplo3(int nbr) {
        return nbr > 0 ?
                lang("The intense explosion impacted " + nbr + " monster" + (nbr == 1 ? "" : "s") + "!",
                "L'intense explosion a impactée " + nbr + " monstre" + (nbr == 1 ? "" : "s") + " !",
                "L'intensa esplosione ha avuto un impatto " + nbr + " mostr" + (nbr == 1 ? "o" : "i") + "!",
                " وحوش" + nbr + "الانفجار الشديد أصاب ") :
                lang("No monster was impacted by the explosion...",
                "Aucun monstre n'a été impacté par l'explosion...",
                "Nessun mostro è stato colpito dall'esplosione...",
                "لم يتأثر أي وحش بالانفجار ...");
    }
    public static String logFreezingScroll1() {
        return lang("Casting this spell in a room would have been more judicious...",
                "Incanter ce sort dans une salle aurait été plus judicieux...",
                "Lanciare questo incantesimo in una stanza sarebbe stato più giudizioso...",
                "كان من الممكن أن يكون إلقاء هذه التعويذة في غرفة أكثر حكمة ...");
    }
    public static String logFreezingScroll2() {
        return lang("The freeze spell does not affect the Merchant.",
                "Le sort de gel n'affecte pas le Marchand.",
                "L'incantesimo congelamento non ha effetto sul Mercante.",
                "لا تؤثر تعويذة التجميد على التاجر.");
    }
    public static String logFreezingScroll3() {
        return lang("The monsters in the room are frozen!",
                "Les monstres de la salle sont gelés !",
                "I mostri nella stanza sono congelati!",
                "تم تجميد الوحوش في الغرفة!");
    }



    // ===== Button Inventory Panel & Items effects ===== //
    public static String buttonEquip() {
        return lang("Equip", "Équiper", "Usare", "تجهيز");
    }
    public static String buttonUnequip() {
        return lang("Unequip", "Déséquiper", "Lasciare", "غير مجهّز");
    }
    public static String buttonConsume() {
        return lang("Consume", "Consommer", "Consumare","يستخدم");
    }
    public static String buttonThrow() {
        return lang("Throw away", "Jeter", "Buttare via", "رمى");
    }
    public static String descriptionItemCons(ConsumableTypes ct) {
        return switch (ct) {
            case HEALTH_POTION -> lang("Heals 10% of your HP", "Soigne 10% de vos PV", "Cura il 10% dei tuoi HP", "+ 10٪ من الأرواح");
            case REGENERATION_POTION -> lang("Heals 10 HP each turn for 6 turns",
                    "Rend 10 PV chaque tour pendant 6 tours",
                    "Guarito 10 HP per turno per 6 turni",
                    "يستعيد 10 أرواح كل منعطف لمدة 6 أدوار");
            case TELEPORT_SCROLL -> lang("Teleports you to the Merchant room",
                    "Vous téléporte chez le Marchand",
                    "Ti teletrasporta dal Mercante",
                    "يأخذك إلى التاجر");
            case DIVINE_BLESSING -> lang("Become invulnerable for 4 turns",
                    "Devenez invulnérable pendant 4 tours",
                    "Diventi invulnerabile per 4 turni",
                    "كن محصنًا لبعض الوقت");
            case DRAGON_EXPLOSION -> lang("Deals 25% of their HP to each monster in the room and burns them",
                    "Inflige 25% de leurs PV à chaque monstre de la salle et les brûle",
                    "Infligge il 25% dei loro HP a ogni mostro nella stanza e li brucia",
                    "يصيب 25٪ من صحته على كل وحش في الغرفة ويحرقه");
            case FREEZING_SCROLL -> lang("Freezes all enemies in the room for 2 turns",
                    "Gèle tous les ennemis dans la salle pendant 2 tours",
                    "Congela tutti i nemici nella stanza per 2 turni",
                    "يجمد كل الأعداء في الغرفة لمرتين");
        };
    }



    // ===== TRANSLATIONS ===== //
    public static String translate(EntityType e) {
        return switch (e) {
            case HERO_WARRIOR -> warriorCL();
            case HERO_ARCHER -> archerCL();
            case HERO_MAGE -> mageCL();
            case ALLY_MERCHANT -> lang("MERCHANT", "MARCHAND", "MERCANTE","تاجر");
            case MONSTER_GOBLIN -> lang("GOBLIN", "GOBELIN", "GOBLIN","جوبيلين");
            case MONSTER_ORC -> lang("ORC", "ORC", "ORCO","مسخ");
            case MONSTER_SPIDER -> lang("SPIDER", "ARAIGNÉE", "RAGNO","عنكبوت");
            case MONSTER_WIZARD -> lang("WIZARD", "SORCIER", "STREGONE","ساحر");
            case MONSTER_BOSS -> lang("BOSS", "BOSS", "BOSS", "رئيس");
        };
    }
    public static String translateHungerState(String state) {
        return switch (state) {
            case "Sated" -> lang("Sated", "Repu", "Sazio","متخم");
            case "Peckish" -> lang("Peckish", "Petit creux", "Poco affamato","جوفاء صغيرة");
            case "Hungry" -> lang("Hungry", "Affamé", "Affamato","جائع");
            case "Starving" -> lang("Starving", "Mort de faim", "Morto di fame","يموت من الجوع");
            default -> "";
        };
    }

    public static String translate(Tools.Victory_Death vd){
        return switch (vd){
            case DEATH_BY_HP -> lang("Death by HP", "Mort par PV", "Morte per HP", "الموت بنقاط الإصابة");
            case DEATH_BY_HUNGER -> lang("Death by hunger", "Mort par famine", "Morte par fame", "الموت بالمجاعة");
            case WIN -> lang("Victory", "Victoire", "Vittoria", "فوز");
            case ABANDON -> lang("Abandon", "Abandon", "Abbandono", "التخلي عن");
        };
    }

    public static String translate(EntityState e) {
        return switch (e) {
            case BURNT -> lang("BURNT", "BRÛLÉ", "BRUCIATO","أحرق");
            case ENRAGED -> lang("ENRAGED", "ENRAGÉ", "INFURIATO","غاضب");
            case FROZEN -> lang("FROZEN", "GELÉ", "CONGELATO","مجمدة");
            case HEALED -> lang("HEALED", "SOIGNÉ", "RIGENERATO","اهتم ب");
            case INVULNERABLE -> lang("INVULNERABLE", "INVULNÉRABLE", "INVULNERABILE","ضار");
            case NEUTRAL -> lang("NEUTRAL", "NEUTRE", "NEUTRO","حيادي");
            case PARALYSED -> lang("PARALYSED", "PARALYSÉ", "PARALIZZATO","مشلولة");
            case POISONED -> lang("POISONED", "EMPOISONNÉ", "AVVELENATO","مسموم");
        };
    }
    public static String translate(EquipmentTypes equT) {
        return switch (equT) {
            case WOODEN_SWORD -> lang("Wooden Sword", "Épée en Bois", "Spada di Legno","سيف خشبي");
            case IRON_SWORD -> lang("Iron Sword", "Épée en Fer", "Spada di Ferro","سيف حديدي");
            case MAGIC_SWORD -> lang("Magic Sword", "Épée Magique", "Spada Magica","سيف سحري");
            case ICY_SWORD -> lang("Icy Sword", "Épée du Gel", "Spada di Ghiaccio","سيف فروست");
            case DRAGON_SWORD -> lang("Dragon Sword", "Épée du Dragon", "Spada del Drago","سيف التنين");

            case SHORT_BOW -> lang("Short Bow", "Arc Court", "Arco Corto","القوس القصير");
            case LONG_BOW -> lang("Long Bow", "Arc Long", "Arco Lungo","القوس الطويل");
            case DRAGON_BOW -> lang("Dragon Bow", "Arc du Dragon", "Arco del Drago","قوس التنين");

            case WOODEN_STAFF -> lang("Wooden Staff", "Sceptre en Bois", "Scettro di Legno","صولجان خشبي");
            case MAGIC_STAFF -> lang("Magic Staff", "Sceptre Magique", "Scettro Magico","الصولجان السحري");
            case DRAGON_STAFF -> lang("Dragon Staff", "Sceptre du Dragon", "Scettro del Drago","صولجان التنين");

            case WOOD_SHIELD -> lang("Wooden Shield", "Bouclier en Bois", "Scudo di Legno","درع خشبي");
            case IRON_SHIELD -> lang("Iron Shield", "Bouclier en Fer", "Scudo di Ferro","درع الحديد");
            case DRAGON_SHIELD -> lang("Dragon Shield", "Bouclier du Dragon", "Scudo del Drago","درع التنين");

            case LEATHER_GAUNTLET -> lang("Leather Gauntlet", "Gantelet en Cuir", "Guanti in Pelle","القفاز الجلدي");
            case LEATHER_ARMBAND -> lang("Leather Armband", "Brassard en Cuir", "Bracciale in Pelle","سوار جلدي");
            case LEATHER_CHESTPLATE -> lang("Leather Chestplate", "Plastron en Cuir", "Pettorale in Pelle","ثدي الجلد");

            case MAGIC_HAT -> lang("Magic Hat", "Chapeau Magique", "Cappello Magico","قبعة سحرية");
            case MAGIC_TUNIC -> lang("Magic Tunic", "Tunique Magique", "Tunica Magica","سترة سحرية");
            case MAGIC_SPHERE -> lang("Magic Sphere", "Sphère Magique", "Sfera Magica","المجال السحري");
        };
    }
    public static String translate(ConsumableTypes consT) {
        return switch (consT) {
            case HEALTH_POTION -> lang("Health Potion", "Potion de Santé", "Pozione di Salute", "جرعة صحية");
            case REGENERATION_POTION -> lang("Regeneration Potion", "Potion de Régénération", "Pozione di Rigenerazione", "جرعة التجديد");
            case TELEPORT_SCROLL -> lang("Teleportation Scroll", "Parchemin de Téléportation", "Teletrasporto", "انتقل من تخاطر");
            case DIVINE_BLESSING -> lang("Divine Blessing", "Bénédiction Divine", "Benedizione Divina","نعمة إلهية");
            case DRAGON_EXPLOSION -> lang("Dragon Explosion", "Explosion Draconique", "Esplosione Draconica","انفجار شديد القسوة");
            case FREEZING_SCROLL -> lang("Freezing Scroll", "Parchemin de Gel", "Pergamena di Brina", "التمرير الجل");
        };
    }
    public static String translate(Theme theme) {
        return switch (theme) {
            case DUNGEON -> lang("Dungeon", "Donjon", "Segreta", "زنزانة");
            case ISLANDS -> lang("Islands", "Îles", "Isole", "جزيرة");
            case FOREST -> lang("Forest", "Forêt", "Foresta", "غابة");
            case MERCHANT -> lang("Store", "Magasin", "Negozio", "المحل");
            case FINAL_BOSS -> lang("Final Boss", "Boss Final", "Boss Finale", "نهائي بوس");
        };
    }
    public static String translate(GameWindow.KeyBindings key) {
        return switch(key) {
            case up -> Language.directions(Move.UP);
            case left -> Language.directions(Move.LEFT);
            case down -> Language.directions(Move.DOWN);
            case right -> Language.directions(Move.RIGHT);
            case action -> lang("Action", "Action", "Azione", "عمل");
            case inventory -> logInventory();
            case options -> options();
            case restart -> restart();
        };
    }



    // ===== Help Menu Panel ===== //
    public static String history() {
        return lang("History", "Histoire", "Storia", "تاريخ");
    }
    public static String description() {
        return lang("Description", "Description", "Descrizione", "وصف");
    }
    public static String keys() {
        return lang("Keys", "Touches", "Tasti", "مفاتيح");
    }
    public static String credits() {
        return lang("Credits", "Crédits", "Crediti", "الاعتمادات");
    }
    public static String item() {
        return lang("Item", "Objet", "Oggetto", "بالموضوع");
    }
    public static String trap() {
        return lang("Trap", "Piège", "Trappola", "فخ");
    }
    public static String enemies() {
        return lang("Enemies", "Ennemis", "Nemici", "أعداء");
    }
    public static String heroes() {
        return lang("Heroes", "Héros", "Eroi", "بطل");
    }
    public static String aboutDescription() {
        return lang("<html><center>" +
                "<h1> Goal of the game </h1></center>" +
                "In this game you are in a world full of monsters<br>" +
                "that you have to kill.<br>" +
                "Each of them has a particularity:" +
                "<ul>" +
                "<li>Orcs are very strong</li>" +
                "<li>Spiders can poison you</li>" +
                "<li>Wizards cast spells that freeze you</li>" +
                "<li>Goblins aren't very strong and they flee if too weak</li>" +
                "</ul>" +
                "Your goal is to explore each stage to find the exit<br>" +
                "such that at the end you will face an awful <b>dragon</b>.<br>" +
                "Walking across the game you can find different items<br>" +
                "allowing you to increase your abilities, for instance<br>" +
                "<ul>" +
                "<li>Coins are useful let you buy items at the Merchant<br></li>" +
                "<li>Equipment allow you to increase your force or defense</li>" +
                "<li>Food restores a bit of HP and Hunger</li>" +
                "<li>Others with different effects</li>" +
                "</ul>" +
                "In the Merchant Room you can buy the items you want<br>" +
                "but each good has a price and you should have enough<br>" +
                "money to get it. You are also allowed to sell your<br>" +
                "items, but at a reduced price.<br>" +
                "Last thing before starting your Quest:<br>" +
                "Good luck and save the world!" +
                "</html>",
                "<html><center>" +
                "<h1> But du jeu </h1></center>" +
                "Dans ce jeu vous êtes dans un monde empli de monstres<br>" +
                "qu'il vous faudra tuer.<br>" +
                "Chacun d'entre eux possède une particularité :" +
                "<ul>" +
                "<li>Les orcs sont très forts</li>" +
                "<li>Les araignées empoisonnent</li>" +
                "<li>Les mages lancent des sorts de gel</li>" +
                "<li>Les gobelins ne sont pas très forts et fuient quand affaiblis</li>" +
                "</ul>" +
                "Votre objectif est d'explorer tous les étages pour en trouver la sortie<br>" +
                "jusqu'à ce que vous trouviez le terrible <b>dragon</b>.<br>" +
                "En avançant dans le jeu, vous trouverez différents objets<br>" +
                "vous permettant d'augmenter vos abilités, dans le détails : <br>" +
                "<ul>" +
                "<li>Les pièces sont utiles pour acheter chez le Marchand<br></li>" +
                "<li>Les équipements augmentent la force ou la défense</li>" +
                "<li>La nourriture restaure un peu de PV et de Faim</li>" +
                "<li>Autres avec des effets différents</li>" +
                "</ul>" +
                "Dans la salle du Marchand, vous pouvez acheter les items que vous<br>" +
                "désirez à condition d'avoir assez d'argent pour l'obtenir. Vous<br>" +
                "avez aussi la possibilité de vendre vos items mais à un prix réduit.<br>" +
                "Dernière chose avant de commencer votre Quête :<br>" +
                "Bonne chance et sauvez le monde !" +
                "</html>",
                "<html><center>" +
                "<h1> Obiettivo del gioco </h1></center>" +
                "In questo gioco sei in mondo pieno di mostri<br>" +
                "che possono ucciderti con la loro forza.<br>" +
                "Ognuno di loro ha una caratteristica:" +
                "<ul>" +
                "<li>Gli orchi sono molto forti</li>" +
                "<li>I ragni ti avvelenano</li>" +
                "<li>I maghi fanno incantesimo paralizzanti</li>" +
                "<li>I goblin non sono molto forti e fuggono se troppo deboli</li>" +
                "</ul>" +
                "Il tuo obiettivo è di esplorare ogni piano per trovare l'uscita<br>" +
                "cossicché alla fine tu possa trovare un temibile drago.<br>" +
                "Durante il gioco puoi trovare diversi oggetti <br>" +
                "che ti permettono di incrementare le tue abilité, per esempio<br>" +
                "<ul>" +
                "<li>Le monete sono utili per comprare oggetti dal mercante<br></li>" +
                "<li>Le armi ti permettono di aumentare la tua forza o la tua difesa</li>" +
                "<li>Il cibo rigenera un po' di HP o di fame</li>" +
                "<li>Altri con differenti effetti</li>" +
                "</ul>" +
                "Nella sala del mercante puoi comprare ciò che vuoi<br>" +
                "ma ogni cosa ha un prezzo e devi avere abbastanza soldi <br>" +
                "per potertelo permettere. Hai anche la possibilità di vendere i tuoi<br>" +
                "oggetti, ma a un prezzo ridotto.<br>" +
                "Ultima cosa prima di cominciare la missione:<br>" +
                "Buona fortuna e salva il mondo !" +
                "</html>",
                "<html> <center>" +
                        "<h1> هدف اللعبة </ h1> </center>" +
                        "في هذه اللعبة أنت في عالم مليء بالوحوش <br>" +
                        "أن عليك أن تقتل. <br>" +
                        "كل منهم له خصوصية:" +
                        "<ul>" +
                        "<li> الأورك قوية جدًا </ li>" +
                        "<li> سم العناكب </ li>" +
                        "<li> السحراء يلقيون نوبات التجميد </ li>" +
                        "<li> العفاريت ليسوا أقوياء جدًا ويهربون عند إضعافهم </ li>" +
                        "</ul>" +
                        "هدفك هو استكشاف جميع الطوابق للعثور على المخرج <br>" +
                        "حتى تجد <b> التنين </ b> الرهيب. <br>" +
                        "كلما تقدمت في اللعبة ، ستجد كائنات مختلفة <br>" +
                        "مما يسمح لك بزيادة مهاراتك بالتفصيل: <br>" +
                        "<ul>" +
                        "<li> العملات المعدنية مفيدة للشراء من التاجر <br> </li>" +
                        "<li> المعدات تزيد من القوة أو الدفاع </ li>" +
                        "<li> الغذاء يعيد بعض الصحة والجوع </ li>" +
                        "<li> آخرون بتأثيرات مختلفة </ li>" +
                        "</ul>" +
                        "في Salle du Marchand يمكنك شراء العناصر التي <br>" +
                        "تريد بشرط أن يكون لديك ما يكفي من المال للحصول عليها. أنت <br>" +
                        "لديك أيضًا إمكانية بيع العناصر الخاصة بك ولكن بسعر مخفض. <br>" +
                        "آخر شيء قبل بدء المهمة: <br>" +
                        "حظا سعيدا وإنقاذ العالم!" +
                        "</html>" );
    }
    public static String charList() {
        return lang("<html><h1><center>List of characters</center></h1></html>",
                "<html><h1><center>Liste des personnages</center></h1></html>",
                "<html><h1><center>Lista dei personaggi</center></h1></html>",
                "<html> <h1> <center> قائمة الأحرف </ Center> </h1> </html>");
    }
    public static String itemList() {
        return lang("<html><h1><center>List of items</center></h1></html>",
                "<html><h1><center>Liste des objets</center></h1></html>",
                "<html><h1><center>Lista degli oggetti</center></h1></html>",
                "<html> <h1> <center> قائمة الكائنات </ مركز> </ h1> </html>");
    }
    public static String mainKeys() {
        return lang("<html><center><h1> Main keys </h1></center></html>",
                "<html><center><h1> Touches principales </h1></center></html>",
                "<html><center><h1> Tasti principali </h1></center></html>",
                "<html> <center> <h1> المفاتيح الرئيسية </ h1> </center> </html>");
    }
    public static String infoQ1() {
        return lang(
                "<html><center><h1> More info about " +
                        GameWindow.KeyBindings.action.key + " key </h1>" +
                "<h2>Attack</h2><br>" +
                "When you press " +
                        GameWindow.KeyBindings.action.key + ", you can see a gray zone (shadow) <br>" +
                "showing you your attack area and a red square.<br>" +
                "You can move this square with your move keys. <br>" +
                "If a monster is under your aim, the square become green & <br>" +
                "if you press " +
                        GameWindow.KeyBindings.action.key + " again, this monster will be hit.<br>" +
                "If you want to quit attack mode, press " +
                        GameWindow.KeyBindings.action.key + "." +
                "</center></html>",
                "<html><center><h1> Plus d'info sur la touche " +
                        GameWindow.KeyBindings.action.key + " </h1>" +
                "<h2>Attaque</h2><br>" +
                "Quand vous appuyez sur " +
                        GameWindow.KeyBindings.action.key + ", une zone grise (ombre) apparaît<br>" +
                "vous indiquant votre portée d'attaque ainsi qu'un carré rouge.<br>" +
                "Vous pouvez déplacer ce carré avec les touches de déplacement. <br>" +
                "Si un monstre est sous la cible, le carré devient vert et <br>" +
                "si vous appuyez à nouveau sur " +
                        GameWindow.KeyBindings.action.key + ", ce monstre sera attaqué.<br>" +
                "Si vous souhaitez sortir du mode attaque, appuyez sur " +
                        GameWindow.KeyBindings.action.key + "." +
                "</center></html>",
                "<html><center><h1> Maggiori informazioni sul tasto " +
                        GameWindow.KeyBindings.action.key + " </h1>" +
                "<h2>Attacco</h2><br>" +
                "Quando premi " +
                        GameWindow.KeyBindings.action.key + ", appare une zona grigia (ombra) <br>" +
                "che ti mostra il tuo raggio d'attacco e un quadrato rosso.<br>" +
                "Puoi spostare questo quadrato con i tasti d'azione <br>" +
                "Se un mostro è sotto la tua mira, il quadrato diventa verde & <br>" +
                "se premi di nuovo " +
                        GameWindow.KeyBindings.action.key + ", questo mostro sarà colpito.<br>" +
                "Se vuoi usicre dalla modalità d'attacco, premi " +
                        GameWindow.KeyBindings.action.key + "." +
                "</center></html>",
                "<html> <center> <h1> مزيد من المعلومات حول المفتاح " +
                        GameWindow.KeyBindings.action.key + " </h1>" +
                        "<h2> هجوم </ h2> <br>" +
                        "عند الضغط على " +
                        GameWindow.KeyBindings.action.key + " ، تظهر منطقة رمادية (ظل) <br>" +
                        "تشير إلى نطاق هجومك بالإضافة إلى مربع أحمر. <br>" +
                        "يمكنك تحريك هذا المربع باستخدام مفاتيح الحركة. <br>" +
                        "إذا كان هناك وحش تحت الهدف ، يتحول المربع إلى اللون الأخضر و <br>" +
                        "إذا ضغطت على " +
                        GameWindow.KeyBindings.action.key + " مرة أخرى ، فسيتم مهاجمة هذا الوحش. <br>" +
                        "إذا كنت تريد الخروج من وضع الهجوم ، فاضغط على " +
                        GameWindow.KeyBindings.action.key + "" +
                        "</center> </html>"
        );
    }
    public static String infoQ2() {
        return lang(
                "<html><center><h2>Merchant</h2><br>" +
                        "When you are in the Merchant room, you can talk with<br>" +
                        "the Merchant setting the aim on him and clicking " +
                        GameWindow.KeyBindings.action.key + ".</center></html>",
                "<html><center><h2>Marchand</h2><br>" +
                        "Quand vous êtes dans la salle du Marchand, vous pouvez lui<br>" +
                        "parler en le ciblant puis en appuyant sur " +
                        GameWindow.KeyBindings.action.key + ".</center></html>",
                "<html><center><h2>Mercante</h2><br>" +
                        "Quando sei dal Mercante, puoi parlare con lui<br>" +
                        "selezionando il tuo obiettivo su di lui e cliccando " +
                        GameWindow.KeyBindings.action.key + ".</center></html>",
                "<html> <center> <h2> التاجر </ h2> <br>" +
                        "عندما تكون في غرفة التاجر ، يمكنك جعله <br>" +
                        "تحدث باستهدافها ثم الضغط على " +
                        GameWindow.KeyBindings.action.key + " </center> </html>"
        );
    }
    public static String directions(Move m) {
        return switch (m) {
            case UP -> lang("Go up", "Allez en haut", "Salire", "يصعد");
            case DOWN -> lang("Go down", "Allez en bas", "Scendere", "انزل");
            case RIGHT -> lang("Go right", "Aller à droite", "Andare a destra", "انعطف يمينا");
            case LEFT -> lang("Go left", "Aller à gauche", "Andare a sinistra", "أذهب يسارا");
        };
    }
    public static String openTheInventory() {
        return lang(
                "Open the inventory",
                "Ouvrir l'inventaire",
                "Andare all'inventario",
                "جرد مفتوحة"
        );
    }
    public static String newGameSameHero() {
        return lang(
                "<html>Start new game<br>with the same hero</html>",
                "<html>Nouvelle partie<br>avec le même héros</html>",
                "<html>Nuova partita<br>con lo stesso eroe</html>",
                "<html> لعبة جديدة <br> بنفس البطل </ html>"
        );
    }
    public static String actionReadBelow() {
        return lang("Action (read below)", "Action (lire ci-dessous)", "Azione (leggi sotto)", "التفاعل (اقرأ أدناه)");
    }
    public static String creditsText() {
        return lang(
                "<html><center>" +
                        "<h1> This project has been realised by </h1><br>" +
                        "<h2>BenAmara Adam<br>" +
                        "Fissore Davide<br>" +
                        "Garnier Quentin<br>" +
                        "Venturelli Antoine<br></h2>" +
                        "Period of realisation: first semester of 2021 <br>" +
                        "Why: TERD UE related to our L3 Info<br>" +
                        "Professor: Rémy Garcia<br>" +
                        "Programming language:" +
                        "</center></html>",
                "<html><center>" +
                        "<h1> Ce projet a été réalisé par </h1><br>" +
                        "<h2>BenAmara Adam <br>" +
                        "Fissore Davide <br>" +
                        "Garnier Quentin <br>" +
                        "Venturelli Antoine<br></h2>" +
                        "Période de réalisation : premier semestre de 2021 <br>" +
                        "Cadre : UE TERD pendant notre L3 Info<br>" +
                        "Professeur : Rémy Garcia<br>" +
                        "Language de programmation :" +
                        "</center></html>",
                "<html><center>" +
                        "<h1> Questo progetto è stato realizzato da </h1><br>" +
                        "<h2>BenAmara Adam <br>" +
                        "Fissore Davide <br>" +
                        "Garnier Quentin <br>" +
                        "Venturelli Antoine<br></h2>" +
                        "Periodo di realizzazione: primo semestre del 2021 <br>" +
                        "Obiettivo: per l'UE TERD durante la nostra L3 Info<br>" +
                        "Professore: Rémy Garcia<br>" +
                        "Linguaggio di programmazione:" +
                        "</center></html>",
                "<html> <center>" +
                        "<h1> تم إنشاء هذا المشروع بواسطة </ h1> <br>" +
                        "<h2> BenAmara Adam <br>" +
                        "Fissore Davide <br>" +
                        "Garnier Quentin <br>" +
                        "Venturelli Antoine <br> </h2>" +
                        "فترة التنفيذ: الفصل الدراسي الأول لعام 2021 <br>" +
                        "Framework: UE TERD أثناء L3 Info <br>" +
                        "المعلم: ريمي جارسيا <br>" +
                        "لغة البرمجة:" +
                        "</center> </html>"
        );
    }
    public static String historyText() {
        return lang(
                "<html>" +
                    "<center><h1> That Time the Hero saved the Village </h1><br>" +
                    "Six hundred years ago, a terrible Evil awoke and ravaged the world.<br>" +
                    "No one could stop him and the hope was slowly fading, until the<br>" +
                    "arrival of a Hero with an unknown name who fought the Evil and<br>" +
                    "won, sending him back to the darkness where he came from.<br>" +
                    "Years passed and peace reigned for a long time, until the fatal day<br>" +
                    "when Evil reappeared.<br>" +
                    "He erected a grim fortress with his powers, near a small isolated village,<br>" +
                    "and is now preparing to finish what he had started six hundred years ago.<br>" +
                    "However, there is never a shadow without light: a brave defender of the<br>" +
                    "village appeared and now enters the dungeon to defeat, as in the legend,<br>" +
                    "the Evil himself.<br>" +
                    "<h3>Today, it's up to you to protect your village<br>" +
                    "against this ancestral threat.<br>" +
                    "Will you be able to become a Hero yourself by entering the legend?</h3></center>" +
                    "</html>",
                "<html>" +
                    "<center><h1> That Time the Hero saved the Village </h1><br>" +
                    "Il y a six cents ans, un Mal terrible se réveilla et ravagea le monde.<br>" +
                    "Nul ne pouvait s'y opposer et l'espoir s'éteignait peu à peu ;<br>" +
                    "puis vint alors un Héros au nom inconnu qui combattit le Mal et<br>" +
                    "en triompha, le renvoyant dans les ténèbres d'où il avait émergé.<br>" +
                    "Les années passèrent et la paix régna, longtemps, jusqu'au jour funeste<br>" +
                    "où le Mal réapparut.<br>" +
                    "Il érigea grâce à ses pouvoirs une forteresse maléfique, tout près <br>" +
                    "d'un petit village isolé, et se prépare désormais à finir ce qu'il<br>" +
                    "avait commencé il y a six cents ans.<br>" +
                    "Cependant, il n'y a jamais d'ombre sans lumière : un brave défenseur<br>" +
                    "du village est apparu et pénètre à présent dans le donjon afin d'y <br>" +
                    "vaincre, tout comme dans la légende, le Mal en personne.<br>" +
                    "<h3>C'est aujourd'hui à vous de protéger votre village<br>" +
                    "contre cette menace ancestrale.<br>" +
                    "Parviendrez-vous à devenir à votre tour un Héros en<br>" +
                    "entrant dans la légende ?</h3></center>" +
                    "</html>",
                "<html>" +
                    "<center><h1> That Time the Hero saved the Village </h1><br>" +
                    "Seicento anni fa, un Male terribile si svegliò e devastò il mondo.<br>" +
                    "Niente poteva opporsi e la speranza si spegneva a poco a poco;<br>" +
                    "venne poi un Eroe dal nome ignoto que combatté il male e<br>" +
                    "trionfò, ributtandolo nelle tenebre da cui era emerso.<br>" +
                    "Gli anni passarono e la pace regnò a lungo fino a quando, <br>" +
                    "un giorno funesto, il male riapparve.<br>" +
                    "Grazie ai suoi poteri erse une fortezza malefica, vicino a <br>" +
                    "un piccolo villaggio isolato e si prepara a terminare ciò che<br>" +
                    "aveva cominciato seicento anni fa.<br>" +
                    "Tuttavia, non c'è mai ombra senza luce: un bravo difensore<br>" +
                    "del villaggio è apparso e discende nella fortezza al fine di <br>" +
                    "vincere, comme dice la leggenda, il Male in persona.<br>" +
                    "<h3>A voi il compito di proteggere il vostro villaggio<br>" +
                    "contro questa minaccia ancestrale.<br>" +
                    "Riuscirate a diventare anche voi un Eroe<br>" +
                    "entrando nella leggenda?</h3></center>" +
                    "</html>",
                "<html>" +
                        "<center> <h1> تلك المرة أنقذ البطل القرية </ h1> <br>" +
                        "منذ ستمائة عام استيقظ شر رهيب ودمّر العالم. <br>" +
                        "لا أحد يستطيع أن يعارضها والأمل يتلاشى تدريجياً <br>" +
                        "ثم جاء بطل باسم غير معروف قاتل الشر <br> <br>" +
                        "انتصر ، وأعاده إلى الظلمة التي خرج منها. <br>" +
                        "مرت السنوات وساد السلام مدة طويلة حتى يوم الموت <br>" +
                        "حيث ظهر الشر مرة أخرى. <br>" +
                        "بنى بقواه حصنًا شريرًا قريبًا جدًا من <br>" +
                        "من قرية صغيرة معزولة ، ويستعد الآن لإنهاء ما <br>" +
                        "بدأت قبل ستمائة عام. <br>" +
                        "ومع ذلك ، لا يوجد ظل بدون ضوء: مدافع شجاع <br>" +
                        "من القرية ظهرت وهي الآن تدخل الزنزانة من أجل <br>" +
                        "قهر ، كما في الأسطورة ، الشر شخصيًا. <br>" +
                        "<h3> يعود الأمر إليك لحماية قريتك اليوم <br>" +
                        "ضد تهديد الأجداد هذا. <br>" +
                        "هل ستصبح بدورها بطلاً في <br>" +
                        "دخول وسيلة الإيضاح؟ </ h3> </center>" +
                        "</html>"
        );
    }

    public static String quitTheGame() {
        return lang("Quit the game", "Quitter le jeu", "Usire dal gioco", "اترك اللعبة");
    }
    public static String restart() {
        return lang("Restart the game", "Relancer la partie", "Ricominciare la partita", "أعد تشغيل اللعبة");
    }
    public static String quitGameConfirmation() {
        return lang("Do you really want to leave the game?",
                "Êtes-vous sûr de vouloir quitter le jeu ?",
                "Sei sicuro di voler chiudere il gioco?",
                "هل أنت متأكد أنك تريد إنهاء اللعبة؟");
    }
    public static String goToMenuConfirmation() {
        return lang("Do you really want to go back to the main menu?",
                "Êtes-vous sûr de vouloir revenir au menu principal ?",
                "Sei sicuro di voler andare al menu principale?",
                "هل أنت متأكد أنك تريد العودة إلى القائمة الرئيسية؟");
    }
    public static String restartConfirmation() {
        return lang("Do you really want to restart the game?",
                "Êtes-vous sûr de vouloir recommencer la partie ?",
                "Sei sicuro di voler ricominciare la partita?",
                "هل أنت متأكد أنك تريد بدء اللعبة من جديد؟");
    }



    // ===== End Panel (Victory or Defeat) =====
    public static String gameOver() {
        return lang("GAME OVER!", "VOUS AVEZ PERDU !", "HAI PERSO!", "انتهت اللعبة!");
    }
    public static String gameVictory() {
        return lang("YOU SAVED THE WORLD FROM DARKNESS!",
                "VOUS AVEZ SAUVÉ LE MONDE DE L'OBSCURITÉ !",
                "HAI SALVATO IL MONDO DALL'OSCURITÀ!",
                "لقد أنقذت العالم من الظلام!");
    }



    // ===== Ranking Panel =====
    public static String clear() {
        return lang("Clear", "Effacer", "Cancellare", "تمحو");
    }
    public static String date() {
        return lang("Date", "Date", "Data", "بتاريخ");
    }
    public static String heroName() {
        return lang("Name", "Nom", "Nome", "الكنية");
    }
    public static String speciality() {
        return lang("Speciality", "Spécialité", "Specialità", "تخصص");
    }
    public static String confirmClear() {
        return lang("You are about to erase the entire ranking history. This action is irreversible.",
                "Vous êtes sur le point d'effacer tout l'historique du classement. Cette action est irréversible.",
                "Stai per cancellare l'intera cronologia della classifica. Questa azione è irreversibile.",
                "أنت على وشك محو سجل الترتيب بالكامل. هذا العمل لا رجوع فيه.");
    }
    public static String erase() {
        return lang("Erase", "Effacer", "Cancellare", "تمحو");
    }
    public static String end() {
        return lang("End", "Fin", "Fine", "نهاية");
    }
    public static String difficulty() {
        return lang("Difficulty", "Difficulté", "Difficoltà", "صعوبة");
    }
    public static String logInvalidFile(){
        return lang("Can't parse the ranking file: it has been cleared.",
                "Le fichier du classement est invalide : il a été réinitialisé.",
                "Il file della classifica é non valido: è stato cancellato.",
                "ملف الترتيب غير صالح ، لقد تم الكتابة فوقه.");
    }
}
