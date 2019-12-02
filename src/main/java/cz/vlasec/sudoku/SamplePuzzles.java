package cz.vlasec.sudoku;

public class SamplePuzzles {
    static final String[] EASY = new String[]{
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

    static final String[] EXPERT = new String[]{
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

    static final String[] EXTREME = new String[]{
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
    static final String[] EXTRA_HARD = new String[] {
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
    static final String[] AI_ESCARGOT = new String[] {
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

    static final String[] EASY16 = new String[]{
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

    static final String[] MEDIUM16 = new String[]{
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

    static final String[] EASY25 = new String[] {
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
    static final String[] HARD25 = new String[]{
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

    static final String[] HARD49 = new String[]{
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

    static final String[] HARD49_STEP3 = new String[]{
            "U e Rk    A2Ia LYJEXPMC 7m    b FhZ13   c 9H i Bd",
            " SKA  21  G  T e R lU Nb F Z9   W  M g7mVnOLYJE  ",
            "1    cfMC 7n   KA I25 H i BdO YJE P N   hZ4e R   ",
            "  g mV 8N 6ZhF3Q  cf1OLYJEX  e  klU9HD W dSKAaI  ",
            "8 b6FhZ9H idBW  7m  M4ejR  USK aI25 L   X 3Q Tcf ",
            "9 Di BdO  J     6F    K aI  3Q Tcf14 jRklUCg7mVnM",
            "OL JEXP   RUl HD WBd93QGTcf1C 7  nMSKA    N 6 hZ8",
            "RklU4eja 2 AK  X  L   Vn Cg F Z  b T f   G Bd9HD ",
            " I  S A c 1GQ3k  4e RFhZ N 6W   H  m nM    XPOLY ",
            "  f 3QGm  M gCI25   a  d HD   P  YJF Z      U4e R",
            "  n  g  h 8 bNcf  QG EXPOLYJ lU  j WBd H i 2   Aa",
            "F   Nb6WB  i H nMCg7 k  4ejR  5SKA E  O Y c 13QG ",
            "W d9   E    Y  Z8  6 I  SK a f1  GTk    jRV MCg  ",
            "   O YJk       d9H  Wc 1  GTV    7m 2  KAahZ Nb6F",
            "klU4e  I 5SaA  P  Y EVnMC  mh   b F f13QGTBd9  iW",
            "  5S A  f 3TGQl     khZ Nb FBd HD    MCg mX  LY E",
            " f13  TV  C  g2 SK aIBd9   W P  YJE   N 6Fl 4   k",
            " nM g7mhZ8 F bf 3 GTcXPO   El 4ejRkB  HD W25  AaI",
            "hZ Nb F  9H iD M  7 V U4e   25SK a XPOL JEf    T ",
            "B  H  W  OL   Z8N 6  2 SK  I 1 Q Tc U4 j kn Cg mV",
            "XP LYJElU4e Rj  H iW   3Q Tcn Cg7 V 5SKAaI  Nb6 h",
            " U4  R 25SKIaA OLY EX  C    Z8N      3QG c 9 Di  ",
            "2 SKA If  Q TG 4 j kl 8    h 9 DiW  MCg7  P    EX",
            "f1 Q   nMC  m 5  AaI2 9  iWBPO    XZ8 b F U ej k ",
            "nMCg7mV    hF    GTc P LYJEX   jR  d HDiW   KAa 2",
            " 8Nb6Fh 9HD  i  g  V U4ejR l5S   I   LYJ X1 QG  f",
            "d  D W POLYXE  Nb FhZ5  A  213 G c    j k   g7m  ",
            "P  Y    4ej kR9 Di    3 GTcfMC 7mVn5S  aI2  b6  Z",
            "  kl  e aI KS  EX O Y mVnMCg6F Z8 bGTcf13Qi B 9HD",
            "Aa  5 KG  f    klU e 6 hZ8Nb W d  D  Vn Cg   P   ",
            "        mV   MaI   K i  d9H  EX OLY  h  NbR lU4  ",
            "JEXP LY klU e W    D       Gm nM g  I 5S  F Z8Nb6",
            "   Z8 bi B  H9mVn Cg7 kl   jaI25SKAJEX O   c 1   ",
            "iWB 9 DJ XPYL  hZ  b  I 5S ATc   QG kl 4 j V M  7",
            "7mV M g6 h  N T  1 QG EX OLY k  4e  W d9H   2 S  ",
            "     lUSKAa  ILY   P      nMNb    8 Q T  1HDiWB  ",
            "S A  25 QGT1 ce    U4N  FhZ H iW  9C 7mV MLYJEX  ",
            " QG c 1Cg7  n   a      iWB 9LY E    b6  Z8  R lU ",
            "C 7mV M b6  ZhQG  f1   JEXPOe R lU   i  d K  I  S",
            "Nb6 hZ  D   dBg7  nM e   lU KA I2 SLYJ    Q T f1 ",
            " Di  d LYJEOPX 6Fh 8NKAaI2    Tcf   jRk  4g7m  MC",
            "L   X       UlDiWBd9HQ   f13g mVnM KAaI     Fh 8N",
            "    l 4   IS 2 JE POLg7m    b6F  8NQG  f    WBd9 ",
            "KAaI25S GT 31fj k U4  6F    DiWB  H   V M   E PO ",
            "Q  cf1   mVC nAaI 5S DiW d9 Y   P    Fh 8N  klU4e",
            " 7   M b6 h 8ZG  f    JEX OL Rk U4  i  d9 A I2  K",
            " 6FhZ  Di B 9d     C j kl  e a 25   JEXP L Tc 1  ",
            "D  Bd HYJEXLOP F Z8NbA  25SK    1  jR l    m  MC ",
            "YJE POLjRk e    B  HD   f13  mVnMC  aI25SK6F Z8 b",
    };

    static final String[] HARD49_STEP2 = new String[]{
            "U e Rk    A2Ia LYJEXPMC 7m    b FhZ13   c 9H i Bd",
            " SKA  21  G  T e R lU Nb FhZ9  iW  M g7mVnOLYJE  ",
            "1    cfMC 7n   KA I25 H i BdO YJE P N   hZ4ejR   ",
            "  g mV 8N 6ZhF3Q  cf1OLYJEX  e  klU9HD W dSKAaI  ",
            "8 b6FhZ9H idBW  7m  M4ejR  USK aI25 L   X 3Q Tcf ",
            "9 Di BdOL J     6F    K aI  3Q Tcf14 jRklUCg7mVnM",
            "OLYJEXP   RUl HDiWBd93QGTcf1C 7  nMSKA    N 6 hZ8",
            "RklU4eja 2 AK  X  L   Vn Cg F Z  b T f   G Bd9HD ",
            " I  S A c 1GQ3k  4e RFhZ N 6W   H  m nM    XPOLY ",
            "  f 3QGm  M gCI25   a  d9HD  XP  YJFhZ      U4e R",
            "  n  g  h 8 bNcf  QG EXPOLYJ lU  j WBd H i 2   Aa",
            "F   Nb6WB  i H nMCg7 k  4ejR  5SKA E  O Y c 13QG ",
            "W d9H  E    Y  Z8  6 I  SK a f1  GTk    jRV MCg7m",
            "   O YJk       d9H  Wc 1  GTV    7m 2  KAahZ Nb6F",
            "klU4e  I 5SaA  P  Y EVnMC  mh   b F f13QGTBd9  iW",
            "  5S A  f 3TGQl     khZ Nb FBd HD    MCg mX  LY E",
            " f13  TV  C  g2 SK aIBd9   W P  YJE   N 6Fl 4   k",
            " nM g7mhZ8 F bf 3 GTcXPO   El 4ejRkB  HD W25  AaI",
            "hZ Nb F  9H iD M  7 V U4e   25SK a XPOL JEf    T ",
            "B  H  W  OLE  Z8N 6  2 SK  I 1 Q Tc U4 j kn Cg mV",
            "XP LYJElU4e Rj  H iW   3Q Tcn Cg7 V 5SKAaI  Nb6 h",
            " U4  R 25SKIaA OLY EX  C    Z8N      3QG c 9 Di  ",
            "2 SKA If  Q TG 4 j kl 8    h 9 DiW  MCg7  P    EX",
            "f1 Q   nMC  m 5  AaI2 9  iWBPO    XZ8 b F U ej k ",
            "nMCg7mV    hF    GTc P LYJEX   jR  d HDiW   KAaI2",
            " 8Nb6Fh 9HD  i  g  V U4ejR l5S   I   LYJ X1 QG  f",
            "d  D W POLYXE  Nb FhZ5  A  213 G c    j k   g7m  ",
            "P  Y  X 4ej kR9 Di  d 3 GTcfMC 7mVn5S  aI2  b6  Z",
            "  klU e aI KS  EX O Y mVnMCg6F Z8 bGTcf13Qi B 9HD",
            "AaI 5 KG  f    klU e 6 hZ8Nb W d  D  Vn Cg   P   ",
            "     3  mVn  MaI   K i  d9H  EX OLY  h  NbR lU4  ",
            "JEXP LY klU e W    D   f   Gm nM g  I 5S  F Z8Nb6",
            "   Z8 bi B  H9mVn Cg7 kl   jaI25SKAJEX O   c 1   ",
            "iWB 9 DJ XPYL  hZ  b  I 5S ATc   QG kl 4 j V M  7",
            "7mV M g6 h  N T  1 QG EX OLY k  4e  W d9H  I2 S  ",
            "     lUSKAa  ILYJ  P      nMNb    8 Q T  1HDiWB  ",
            "S A  25 QGT1 ce    U4N  FhZ H iW  9C 7mV MLYJEX  ",
            " QG c 1Cg7  n   a      iWBd9LY E P  b6  Z8  R lU ",
            "C 7mV M b6  ZhQG  f1   JEXPOe R lU   i  d K  I  S",
            "Nb6 hZ  D   dBg7  nM e   lU KA I2 SLYJ    Q T f1 ",
            " Di  d LYJEOPX 6Fh 8NKAaI2    Tcf   jRk  4g7m  MC",
            "L   X       UlDiWBd9HQ   f13g mVnM KAaI     Fh 8N",
            "    l 4   IS 2 JE POLg7m    b6F  8NQG  f    WBd9 ",
            "KAaI25S GT 31fj k U4  6F    DiWB  H   V M   E PO ",
            "Q  cf1   mVC nAaI25S DiW d9 Y   P    Fh 8N  klU4e",
            " 7   M b6 h 8ZG  f    JEX OL Rk U4  i  d9 A I2  K",
            " 6FhZ  Di B 9d     C j kl  e a 25   JEXP L Tc 1  ",
            "D  Bd HYJEXLOP F Z8NbA  25SK    1  jR l    m  MC ",
            "YJE POLjRk e    B  HD   f13  mVnMC  aI25SK6F Z8 b",
    };

    static final String[] HARD49_STEP1 = new String[]{
            "U.e.Rk....A2Ia.LYJEXPMC.7m....b.FhZ13...c.9H.i.Bd",
            ".SKA..21..Gf.T.e.R.lU.Nb.FhZ9..iW..M.g7mVnOLYJE..",
            "1..G.cfMC.7n...KA.I25.H.iWBdO.YJE.P.N...hZ4ejR...",
            "..g.mV.8N.6ZhF3Q..cf1OLYJEX..e..klU9HD.W.dSKAaI..",
            "8.b6FhZ9H.idBW..7m..M4ejR.lUSK.aI25.L...X.3Q.Tcf.",
            "9.Di.BdOL.J.....6F....K.aI..3Q.Tcf14.jRklUCg7mVnM",
            "OLYJEXP...RUl.HDiWBd93QGTcf1C.7..nMSKA....N.6.hZ8",
            "RklU4eja.2.AK..X..L...Vn.Cg7F.Z..b.T.f...G.Bd9HD.",
            ".I..S.A.c.1GQ3k..4e.RFhZ.N.6W...H..m.nM....XPOLY.",
            "..f.3QGm..M.gCI25...a..d9HD..XP..YJFhZ......U4e.R",
            "..n..g..h.8.bNcf..QG.EXPOLYJ.lU..j.WBd.H.i.2...Aa",
            "F...Nb6WB..i.H.nMCg7.k..4ejR..5SKA.EX.O.Y.c.13QG.",
            "W.d9H..E....Y..Z8..6.I..SK.a.f1..GTk....jRV.MCg7m",
            "...O.YJk.......d9H..Wc.1..GTV....7m.2..KAahZ.Nb6F",
            "klU4e..I.5SaA..P..Y.EVnMC..mh...b.F.f13QGTBd9..iW",
            "..5S.A..f.3TGQl.....khZ.Nb.FBd.HD....MCg.mX..LY.E",
            ".f13..TV..C..g2.SK.aIBd9...W.P..YJE...N.6Fl.4...k",
            ".nM.g7mhZ8.F.bf.3.GTcXPO...El.4ejRkB..HD.W25..AaI",
            "hZ.Nb.F..9H.iD.MC.7.V.U4e...25SK.a.XPOL.JEf....T.",
            "B..H..W..OLE..Z8N.6..2.SK..I.1.Q.Tc.U4.j.kn.Cg.mV",
            "XP.LYJElU4e.Rj..H.iW...3Q.Tcn.Cg7.V.5SKAaI..Nb6.h",
            ".U4..R.25SKIaA.OLY.EX..C....Z8N......3QG.c.9.Di..",
            "2.SKA.If..Q.TG.4.jRkl.8....h.9.DiW..MCg7..P....EX",
            "f1.Q...nMC..m.5..AaI2.9..iWBPO....XZ8.b6F.U.ej.k.",
            "nMCg7mV....hF....GTc.P.LYJEX...jR..d.HDiW...KAaI2",
            ".8Nb6Fh.9HD..i..g..V.U4ejR.l5S...I...LYJ.X1.QG..f",
            "d..D.W.POLYXE..Nb.FhZ5..A..213.G.c...ej.k...g7m..",
            "P..Y..X.4ej.kR9.Di..d.3.GTcfMC.7mVn5S..aI2..b6..Z",
            "..klU.e.aI.KS5.EX.O.Y.mVnMCg6F.Z8.bGTcf13Qi.B.9HD",
            "AaI25.KG..f....klU.e.6.hZ8Nb.W.d..D..Vn.Cg...P...",
            ".....3..mVn..MaI...K.i..d9H..EX.OLY..h..NbR.lU4..",
            "JEXP.LY.klU.e.W...HD...f..QGm.nM.g..I.5S..F.Z8Nb6",
            "...Z8.bi.B..H9mVn.Cg7.kl...jaI25SKAJEX.O...c.1...",
            "iWB.9.DJ.XPYL..hZ..b..I.5S.ATc...QG.kl.4.j.V.M..7",
            "7mV.M.g6.h..N.Tc.1.QG.EX.OLY.k.U4e..W.d9H..I2.S.A",
            ".....lUSKAa..ILYJ..P......nMNb....8.Q.T..1HDiWB..",
            "S.A..25.QGT1.ce....U4N..FhZ.H.iW..9C.7mV.MLYJEX..",
            ".QG.c.1Cg7..n...a......iWBd9LY.E.P..b6..Z8..R.lU.",
            "C.7mVnM.b6..ZhQG..f1...JEXPOe.R.lU...i..d.K..I..S",
            "Nb6.hZ..D...dBg7..nM.e...lU.KA.I2.SLYJ....Q.T.f1.",
            ".Di..d.LYJEOPX.6Fh.8NKAaI2....Tcf...jRk..4g7m..MC",
            "L...X.......UlDiWBd9HQ...f13g.mVnM.KAaI.....Fh.8N",
            "....l.4...IS.2.JE.POLg7m...Cb6F..8NQG..f....WBd9.",
            "KAaI25SQGT.31fj.k.U4..6F....DiWB..H...V.M...E.PO.",
            "Q..cf1...mVC.nAaI25S.DiW.d9.Y...P....Fh.8N..klU4e",
            ".7...M.b6.h.8ZG..f....JEX.OL.Rk.U4..i..d9.A.I2..K",
            ".6FhZ..Di.B.9d.....C.j.kl..e.a.25...JEXP.L.Tc.1..",
            "D..Bd.HYJEXLOP.F.Z8NbA..25SK....1..jR.l....m..MC.",
            "YJE.POLjRk.e4...B..HD...f13..mVnMC..aI25SK6F.Z8.b",
    };

    static final String[] FAIL49 = new String[] {
            "..e.Rk....A2Ia.LYJEXPMC.7m....b.FhZ13...c.9H.i.Bd",
            ".SKA..21..Gf.T.e.R.l..Nb.FhZ9..iW..M.g7mVn.LYJE..",
            "1..G.cfMC.7....KA.I25.H.iWB.O..JE.P.....hZ4ejR...",
            "..g.mV.8N.6ZhF3Q..cf.OLYJEX..e...lU9HD.W.dSKAaI..",
            "..b6FhZ.H.idBW..7m..M4ejR.lUSK.aI25.....X.3Q.Tcf.",
            "9.D..BdO..J.....6F......aI..3Q.Tcf14.jRklUCg7mVnM",
            "OLY.EXP...RUl.HDiWBd93QGTcf1C.7..nMSKA....N.6.hZ8",
            "RklU4eja.2.AK..X..L...Vn.Cg7F.Z..b.T.f...G.Bd9HD.",
            ".I..S...c.1GQ3k..4e.RF.Z.N.6W...H..m.nM....XPOLY.",
            "..f.3QGm..M.gCI25...a..d9HD..XP..YJFhZ......U4e.R",
            "..n..g..h.8.b.cf..QG.EXPOLYJ.lU..j.WBd.H...2...Aa",
            "F...Nb6WB..i.H.nMCg7.k...ejR..5SKA.EX.O.Y.c.13QG.",
            "..d9H..E....Y..Z8....I..SK.a.f...GTk....jRV.M..7m",
            "...O.YJk.......d9H..Wc.1..GTV....7m.2..KAahZ.N.6F",
            "klU4e..I.5SaA..P..Y.EVnMC..mh...b.F.f13QGTBd9..iW",
            "..5S.A..f.3TGQl.....khZ.Nb.FBd.HD....MCg.mX..LY.E",
            ".f13..TV..C..g2.SK.a.Bd9...W.P..YJE...N.6Fl.4...k",
            ".nM.g7mhZ8.F.bf.3..TcXPO...El.4ejRkB..HD.W25..AaI",
            "hZ.Nb.F..9H.iD.MC.7.V.U4e...25SK.a.XPOL.JEf......",
            "B..H..W..OLE..Z8N.6..2.S...I.1.Q.Tc.U4.j.kn.Cg..V",
            "XP..YJElU4e.Rj....iW...3Q.Tcn.Cg7.V...KAaI..Nb6..",
            ".U4....25SKIaA.OLY.EX..C....Z8N......3QG.c.9.Di..",
            "2.SKA..f..Q.TG.4.jRkl.8....h.9.DiW..MCg7..P....EX",
            "f1.....nMC..m.5...aI2.9..iWBPO....XZ8.b6F.U.ej.k.",
            "nMCg7mV....hF....GTc.P.LYJ.X...jR..d.HDiW...KAaI2",
            ".8Nb6Fh.9HD..i..g..V.U4ejR.l5S...I...LYJ.X1.QG..f",
            "d..D.W.POL.XE..Nb.FhZ5..A..213.G.c...ej.k...g7m..",
            "P..Y..X.4ej.kR9.Di....3.GTcfMC.7.Vn5S..aI2..b6..Z",
            "..klU.e.aI.KS5.EX.O.Y.mVnMCg6F..8.bGTcf13Qi.B.9HD",
            "AaI25.KG..f....kl..e.6.hZ8Nb.W.d.....Vn.Cg.......",
            ".....3..mVn..MaI...K.i..d9H..EX.OLY..h...bR..U4..",
            "J.XP.LY.k.U.e.W...HD...f..QGm.nM.g..I.5S..F.Z8Nb6",
            "...Z8..i.B..H9mVn.Cg7.kl...jaI25SKAJEX.O...c.1...",
            "iWB.9..J.XPYL..hZ..b..I.5S.ATc...QG.kl.4.j.V.M..7",
            "7mV.M.g..h..N.Tc.1.QG.EX.OLY.k.U4e..W.d9H..I2.S.A",
            ".....lUSKAa..I.YJ..P......nMNb....8.Q.T..1HDiW...",
            "..A..25.QGT1.ce....U4N..FhZ.H.iW..9C.7mV.MLYJEX..",
            "..G...1Cg...n...a......iWBd9.Y......b6..Z8..R.lU.",
            "C.7mVnM.b...ZhQG..f1...JEXPOe.R.lU...i..d.K..I..S",
            "Nb6.hZ..D...dBg7..nM.e...lU.KA.I2.SL.J....Q.T.f1.",
            ".D...d.LYJEOPX.6Fh.8N.AaI2....Tcf...jRk..4g7m..MC",
            "L...X.......UlDiWBd9HQ...f13g.mVnM.KAaI......h.8N",
            "....l.4...I..2.JE.POLg7m...Cb6F..8NQG..f....WBd9.",
            "KAaI25SQGT.31fj.k.U4..6F....DiWB..H...V.M...E.PO.",
            "Q..cf1...mVC.nAaI25S.DiW.d9.Y...P.....h.8N..klU4e",
            ".7...M.b6.h.8ZG..f....JEX.OL.Rk.U4..i..d9...I2..K",
            ".6Fh...Di.B.9d.....C.j.kl..e.a.25...J.XP.L.Tc.1..",
            "D..Bd.HYJE.LOP.F.Z8NbA..25SK....1..jR.l....m..MC.",
            "YJE.POLjRk.e4...B..HD...f13..mVnMC..aI25SK6F.Z8.b",
    };
}
