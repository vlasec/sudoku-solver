package cz.vlasec.sudoku;

import cz.vlasec.sudoku.core.board.Board;
import cz.vlasec.sudoku.core.board.Problem;
import cz.vlasec.sudoku.core.board.Rules;
import cz.vlasec.sudoku.core.solver.Solver;
import cz.vlasec.sudoku.rules.ClassicRules;
import cz.vlasec.sudoku.solvers.ClassicSolver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static final List<String> ALPHADOKU = Collections.unmodifiableList(Arrays.asList(
            "A", "B", "C", "D", "E",
            "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O",
            "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y"
    ));

    private static final String[] EASY = new String[]{
            "9...6...3",
            "1.5.932.6",
            ".4..5...9",
            "8.....471",
            "..487....",
            "7.26.1..8",
            "2........",
            "5...32.94",
            ".87.1635.",
    };

    private static final String[] EXPERT = new String[]{
            "7...984..",
            "..5..1..3",
            "...2...96",
            ".1.....7.",
            "..7.5....",
            "3...8....",
            "2..4...38",
            ".4.7.....",
            "......6.7",
    };

    private static final String[] EXTREME = new String[]{
            ".2..1..9.",
            "1..8.3..5",
            "..5...8..",
            ".5..8..4.",
            "3..1.2..8",
            ".6..5..3.",
            "..7...4..",
            "2..7.5..9",
            ".1..3..5.",
    };

    // from https://cboard.cprogramming.com/cplusplus-programming/117005-25x25-sudoku.html
    private static final String[] EXTRA_HARD = new String[] {
            "362.....7",
            "....3...6",
            "...5.....",
            "...8..4.1",
            ".1.6.3.7.",
            "2.3..9...",
            ".....7...",
            "9...8....",
            "4.....159",
    };

    // https://usatoday30.usatoday.com/news/offbeat/2006-11-06-sudoku_x.htm
    // allegedly extremely hard ...
    private static final String[] AI_ESCARGOT = new String[] {
            "85...24..",
            "72......9",
            "..4......",
            "...1.7..2",
            "3.5...9..",
            ".4.......",
            "....8..7.",
            ".17......",
            "....36.4.",
    };

    private static final String[] EASY16 = new String[]{
            "..GB81.72..3E...",
            ".......9....3.5.",
            ".7.....5.64....F",
            "....6...5.B...1G",
            "......C64AF.17..",
            "2G76.....C......",
            "..C..2...B...84.",
            ".D59.B..7...A..3",
            "3.6.C...9...5AG.",
            "5.F.D...C.8..B2.",
            ".BD.A..314..C.E.",
            "..87.9...5......",
            "..9..8........B.",
            "GA.5..7..D.6.1.E",
            ".41D..B..7..9G.2",
            "....3G6C.1..D...",
    };

    private static final String[] MEDIUM16 = new String[]{
            ".4..8.G.B.1..6.E",
            "3..9.F.4A7.D..C.",
            ".6..2B..8...3...",
            "F......D..E..2.4",

            "...8.3..7...5...",
            "......E....A.G.D",
            "..925....8..B...",
            "C.1....G..D...3.",

            "...CF....6..D.47",
            "G1D.A..3..7..9..",
            "6.....B.4..9..2A",
            ".3...G...E8..B..",

            "5B3A...8..C4...1",
            ".E..B9...DF.4.5.",
            "D.4.6......2.C.9",
            ".C.G.D...1..6.F.",
    };

    private static final String[] EASY25 = new String[] {
            "....X.T..YVH.RO.A.CIBW.LM",
            ".L..A..UJ...C.SVND..XRQ.K",
            "GRTE..CN.M.X..I.Q.FW.S.Y.",
            "V..HI.WLD.KGJNP..S.Y.TFO.",
            "KMDU.P.S.ABLF....T.O.E...",
            ".CP...K.Y.HJR.DN..B..QWA.",
            "M.FX....OQ...STED..VL.PRB",
            "OSRA..EBC...UI...FWKT.YG.",
            "BJW.HN.DVILC.....GY..F..U",
            "L..I.GA..TM..Y.QSJ...XNKE",
            ".T.OCL..F...P.HUMBK.D.GXI",
            "..X.RU.A.JQES..D.O.N.L.B.",
            "PG.NL..K.EAUY...FW.QJ.HV.",
            ".WQ...D.MVCN.KXTGI.EAP...",
            "D.A..TXOSG....L.J.RH..C..",
            "C..TJV.F.S.PD..MBKH.Y..I.",
            "Y..FBJQ.PUN.T.AC..E.OM..R",
            "E.GM...RN.U..HC...QSWAVT.",
            "WQV.DBY..H.O.XF.IL...CU.J",
            "U..L.X.TE.WI......APKD.NQ",
            "..S.TY.WLN.VHJRI...GM..DO",
            "QFMKYO.HUP...AEB..TC.IX..",
            ".N.P.EMC...K.Q..LA..H.SJ.",
            ".HBJWRI.Q..T..N.X.SMGUK.P",
            "I...U..G.XOB.FWYR.P..NTEC",
    };
    // from https://cboard.cprogramming.com/cplusplus-programming/117005-25x25-sudoku.html
    private static final String[] HARD25 = new String[]{
            "..LF..G.R.EX.JA..D.......",
            "B.S.M...J........RE.....A",
            ".......V....C.B..NL.PHY..",
            ".P...BW..MLV...UOSC....N.",
            "W.X.....YHD.PSU..G...CL.I",
            ".D.B.......J.XLQP...E....",
            "..I..FY...H.EC......T..RS",
            "O.JK...RLS.......W..G..D.",
            ".......N.V..RPT.FKM......",
            ".V.Y..AQEDG..N.HCU..K...F",
            ".TMO......I..B.Y.AH..E.U.",
            ".A....PJ.G..DT..I..N.X.Q.",
            "YBE.....M.....V.....SAH..",
            "..GU..L.BQ...RFP..O..M.J.",
            "HJRLPI...E....S..Q.U.O..V",
            ".H..O.C.F.U..G.RNE.A.....",
            "...S.A.PK...JVYO......U..",
            ".CA.U..D....B.M.XY..N..F.",
            ".......O.LN.FQX.......M..",
            ".EWPD.MXGB.I..OC.V......H",
            "..YTB.S....A....UC..L....",
            "PL.E.KU.W..O....SI.....YJ",
            "....ITVGD.C.NYR.K.....A.O",
            "X.F.VH.YN.JK.I.TAP.G.W..M",
            "NMUA..E...F.V.WJ...B..RGK",
    };

    private static final String[] HARD49 = new String[]{
            "U.e.Rk....A2Ia..YJEXPMC.7m......FhZ13Q..c.9H.i.Bd",
            ".S.A.I21..Gf.T.e...lU.Nb.FhZ9..iW..M.g7mV.OLYJE..",
            "1..G.cfM..7n...KA.I.5.H.iWBdO..J..P.N...hZ..jR...",
            "..g....8N.6ZhF3Q..cf1OLYJEXP.e..klU9HD.WBdSKAaI..",
            "8.b6.hZ9HDidBW..7...M4ejR.lUSK.aI2..LY..X.3Q..cf.",
            "9.Di.B.OL..P....6F....K..I..3QGTcf.4.jRklUCg7mVnM",
            "OLYJEXP.e.RUl..DiWBd93QGTcf1C....nMS.A....N.6FhZ8",
            ".klU4eja.2.AK..X..L...V..Cg7F.Z..b.T.f...G.B.9H..",
            ".I.5S.A...1GQ3k..4e.R.hZ.N.6W...H..mVnM....XPO.Y.",
            "..f.3.Gm..M..CI25...a..d9HD..XP..YJFhZ......U4..R",
            "..n..g..h.8.bNcf1.Q..E.POLYJ..U..j.WBd.H.i.2...Aa",
            "F...Nb6.B..i.H.nMCg7.k..4ejR..5SKA.EX.O.YJc.13QGT",
            "W.d9HD.E....Y..Z...6.I..SK.a.f1...Tk..4.jRV.MCg7m",
            "...O.YJk....j.Bd9H.iWc.1..GTV.M.g......KAahZ.Nb6F",
            "klU4e..I..S.A..P...JEV.MC..mh.....F.f13QGT..9..iW",
            "..5.....f.3TGQl.....khZ.N.6FBd.HD....MCg.mX..LY.E",
            ".f.3...V.....g2.SK.aIB.9..iW.P.LYJE...N.6Fl.4...k",
            ".nM.g7...8.F.bf.3..TcXPO...E...ejRkB..HD.W25..AaI",
            "h..Nb....9HWiDnMC.7.V.U4ejR.25SK.a.XPOL.JEf....T.",
            "B..H..W..OLE..Z8N.6F.2.SK..If1.Q.Tc.U4.j.kn.Cg.m.",
            "XP.LYJElU4e.Rjd.H.iW...3Q.Tc..Cg7.V.5.KAaI..Nb6.h",
            ".U4..R.25.KIaAPO.Y.EX..C....Z8N......3QG.c.9.Di..",
            "..S.A.If....TG.4.jRkl.8....h.9HDiW..M.g7..P....EX",
            "f1.....nMC..m75..Aa.2.9.DiWBPO....XZ..b6F.U.ej.k.",
            "nMCg7mVZ...hF.1..GTc.P.LYJEX...jRk.d.H.iW...KAaI2",
            ".8Nb6Fh.9HD..i.Cg....U.ejR.l.S...I...LYJ.X1.QG..f",
            "d..D.W.POLYXEJ.Nb.FhZ5..A..213.G.c...ej.k...g.m..",
            "P.LY..X.4ej.kR9.Di..d.3.GTcfMC.7mVn5SKAaI2...6..Z",
            "j.k.U.e.aI.KS..E....Y.mV.MCg6F.Z..bGTcf13.i.B.9HD",
            "A.I25.KG..f....klU.e.6..Z8Nb.W.d.....Vn.C.J..P..Y",
            ".T..13..mVn...aI.....i...9H..EX.OLY..h..NbR.lU4e.",
            "JEXP.L..klU.e.....HD...f..QGmVnM....I.5S..F.Z8Nb6",
            "6..Z8..i.B..H.m.n.Cg7..lU..jaI..SKAJEX.O...c.1...",
            "iWB.9.DJ.XPYL..hZ..b..I.5S.A.c...QGR.l.4.j.V.M...",
            "7mV.M.g6.h..N.Tc.1..G.EX..LY.k.U4e.iW...H..I2.S.A",
            ".....lUSKAa..ILYJ..PO.....nMNb..hZ8.Q.T..1HDiWB.9",
            "S.A..25.QGT1..e....U4N..FhZ.H.iW...Cg7mV..L.JEX..",
            ".QG.c.1.g...n...a......iWB.9LY.E.P.Nb6..Z8..R.lU.",
            "C.7mVn..b6...hQG..f1...JEXPOe.R.lU...i..d.K.aI..S",
            "Nb6.hZ..D...dBg7m..M.e...lU.KA.I2.S.YJ......Tcf1.",
            ".Di..d.LYJ.OPX.6Fh.8N.AaI2....Tcf1.ejRk..4g7m.nMC",
            "L...X....R..UlDi.Bd9.Q..c.13g.mVnM.KAaI.....Fh.8N",
            ".j.kl.4...IS.2.JE.POLg7m...Cb.F...NQG..f....WBd9.",
            "KAaI.5SQGT.31fj.k.U4eb6.....DiWB..H.7.V.M...E.PO.",
            "Q..cf13..mVC.nAaI25S..iW.d9.Y...P...6Fh..N..klU4e",
            ".7m..M.b6.h.8ZG..f....JEX.OL.Rk.U4..i..d9.A.....K",
            "..FhZ..D..BH9d.....C.j.kl..e.a.25...J..POL.Tc.13.",
            "D..Bd..YJEXLO..F.Z8NbA..25SK.Tcf1..jR..U.....nMC.",
            "YJE.PO.j.k.e4...B..HD...f13.7mVnMC.AaI25SK6F.Z8Nb",
    };

    public static void main(String[] args) {
        System.out.println("Hello, world! Let's solve some sudoku ...");
//        Rules rules = new ClassicRules(3, 3);
//        Rules rules = new ClassicRules(4, 4);
//        Rules rules = new ClassicRules(5, 5, ALPHADOKU);
        Rules rules = new ClassicRules(7, 7);
        Solver solver = new ClassicSolver();
        Problem problem = Problem.createProblem(rules);
//        applyArrayToGrid(problem, HARD25);
        applyArrayToGrid(problem, HARD49);
//        applyFileToGrid(problem, "killer49x49.txt", 0.9d);

        System.out.println("Today we are solving this puzzle:");
        System.out.println(problem.getBoard());
        List<Board> results = solver.solve(problem.getBoard(), problem.getRules());
        System.out.println("The solution(s) were following:");
        results.forEach(System.out::println);
    }

    private static void applyArrayToGrid(Problem problem, String[] rows) {
        int total = problem.getRules().xSize() * problem.getRules().ySize();
        int blanks = total;
        for (int i = 0; i < rows.length; i++) {
            String row = rows[i];
            for (int j = 0; j < row.length(); j++) {
                char ch = row.charAt(j);
                if (ch != '.') {
                    problem.setValue(problem.getBoard().tileAt(i, j), getValue(problem, "" + ch));
                    blanks--;
                }
            }
        }
        System.out.println("Out of " + total + " fields, " + blanks + " are blank.");
    }

    private static void applyFileToGrid(Problem problem, String resourceFileName, double threshold) {
        Random rnd = new Random();
        try (
                InputStream stream = Main.class.getResourceAsStream(resourceFileName);
                InputStreamReader rawReader = new InputStreamReader(stream);
                BufferedReader reader = new BufferedReader(rawReader);
        ) {
            int total = problem.getRules().xSize() * problem.getRules().ySize();
            int blanks = total;
            for (int i = 0; i < problem.getRules().xSize(); i++) {
                for (int j = 0; j < problem.getRules().ySize(); j++) {
                    String line = reader.readLine();
                    Integer x = (line != null && !"".equals(line.trim())) ? Integer.valueOf(line) : null;
                    if (x != null && rnd.nextDouble() < threshold) {
                        blanks--;
                        problem.setValue(problem.getBoard().tileAt(i, j), getValue(problem, ClassicRules.descriptValue(x)));
                    }
                }
            }
            System.out.println("Out of " + total + "fields, " + blanks + " are blank.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Rules.Value getValue(Problem problem, String val) {
        return problem.getRules().values().stream()
                .filter(v -> v.getDescription().equals(val))
                .findAny().orElseThrow(() -> new IllegalStateException("No value " + val
                        + " found in values: " + problem.getRules().values()));
//        return problem.getRules().values().stream()
//                .filter(v -> v.getValue() == Character.getNumericValue(ch))
//                .findAny().orElseThrow(() -> new IllegalStateException("No value " + Character.getNumericValue(ch)
//                        + " found in values: " + problem.getRules().values()));
    }
}
