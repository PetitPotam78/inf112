import glob as _glob
from reportlab.lib.pagesizes import A4
from reportlab.lib.styles import getSampleStyleSheet, ParagraphStyle
from reportlab.lib.units import cm
from reportlab.lib import colors
from reportlab.platypus import (
    SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle, HRFlowable, Image
)
from reportlab.lib.enums import TA_CENTER, TA_LEFT, TA_JUSTIFY
from datetime import date

OUTPUT = "/home/wissam/projects/inf112/ziani-lahouassa-inf112/compte_rendu_tests.pdf"

doc = SimpleDocTemplate(
    OUTPUT,
    pagesize=A4,
    leftMargin=2.5 * cm,
    rightMargin=2.5 * cm,
    topMargin=2.5 * cm,
    bottomMargin=2.5 * cm,
)

styles = getSampleStyleSheet()

title_style = ParagraphStyle(
    "Title2",
    parent=styles["Title"],
    fontSize=20,
    spaceAfter=6,
    textColor=colors.HexColor("#1a1a2e"),
    alignment=TA_CENTER,
)
subtitle_style = ParagraphStyle(
    "Subtitle",
    parent=styles["Normal"],
    fontSize=11,
    textColor=colors.HexColor("#555555"),
    alignment=TA_CENTER,
    spaceAfter=4,
)
h1_style = ParagraphStyle(
    "H1",
    parent=styles["Heading1"],
    fontSize=14,
    textColor=colors.HexColor("#1a1a2e"),
    spaceBefore=18,
    spaceAfter=6,
    borderPad=4,
)
h2_style = ParagraphStyle(
    "H2",
    parent=styles["Heading2"],
    fontSize=12,
    textColor=colors.HexColor("#16213e"),
    spaceBefore=12,
    spaceAfter=4,
)
body_style = ParagraphStyle(
    "Body2",
    parent=styles["Normal"],
    fontSize=10,
    leading=15,
    spaceAfter=6,
    alignment=TA_JUSTIFY,
)
code_style = ParagraphStyle(
    "Code2",
    parent=styles["Code"],
    fontSize=8.5,
    leading=13,
    backColor=colors.HexColor("#f4f4f4"),
    borderPad=6,
    spaceAfter=8,
)
bullet_style = ParagraphStyle(
    "Bullet2",
    parent=styles["Normal"],
    fontSize=10,
    leading=14,
    leftIndent=18,
    spaceAfter=3,
)

GREEN = colors.HexColor("#2ecc71")
RED   = colors.HexColor("#e74c3c")
BLUE  = colors.HexColor("#2980b9")
LIGHT = colors.HexColor("#ecf0f1")
DARK  = colors.HexColor("#1a1a2e")


def hr():
    return HRFlowable(width="100%", thickness=1, color=colors.HexColor("#cccccc"), spaceAfter=6)


def result_table(rows):
    col_widths = [5.5 * cm, 3.5 * cm, 3.5 * cm, 3 * cm]
    t = Table([["Suite de tests", "Tests effectués", "Erreurs détectées", "Statut"]] + rows,
              colWidths=col_widths)
    t.setStyle(TableStyle([
        ("BACKGROUND",    (0, 0), (-1, 0), DARK),
        ("TEXTCOLOR",     (0, 0), (-1, 0), colors.white),
        ("FONTNAME",      (0, 0), (-1, 0), "Helvetica-Bold"),
        ("FONTSIZE",      (0, 0), (-1, 0), 10),
        ("ALIGN",         (0, 0), (-1, -1), "CENTER"),
        ("VALIGN",        (0, 0), (-1, -1), "MIDDLE"),
        ("ROWBACKGROUNDS",(0, 1), (-1, -1), [LIGHT, colors.white]),
        ("FONTSIZE",      (0, 1), (-1, -1), 9),
        ("GRID",          (0, 0), (-1, -1), 0.4, colors.HexColor("#aaaaaa")),
        ("TOPPADDING",    (0, 0), (-1, -1), 6),
        ("BOTTOMPADDING", (0, 0), (-1, -1), 6),
        ("TEXTCOLOR",     (3, 1), (3, -1), GREEN),
        ("FONTNAME",      (3, 1), (3, -1), "Helvetica-Bold"),
    ]))
    return t


story = []

# ── Title block ──────────────────────────────────────────────────────────────
story.append(Spacer(1, 0.5 * cm))
story.append(Paragraph("Compte-rendu des Tests", title_style))
story.append(Paragraph("Projet SocialNetwork — INF112", subtitle_style))
story.append(Paragraph(f"LAHOUASSA Wissam &amp; ZIANI — {date.today().strftime('%d %B %Y')}", subtitle_style))
story.append(Spacer(1, 0.3 * cm))
story.append(hr())
story.append(Spacer(1, 0.4 * cm))

# ── 1. Introduction ───────────────────────────────────────────────────────────
story.append(Paragraph("1. Introduction", h1_style))
story.append(Paragraph(
    "Ce document présente le compte-rendu des tests unitaires réalisés pour le projet "
    "<b>SocialNetwork</b> dans le cadre du cours INF112. "
    "Les tests couvrent l'initialisation du réseau social, l'ajout de membres et "
    "l'ajout de livres. Ils ont été exécutés via la classe <i>SocialNetworkTest</i> "
    "qui agrège les résultats de trois suites de tests distinctes.",
    body_style,
))
story.append(Paragraph(
    "La sortie de l'exécution est la suivante :",
    body_style,
))
import PIL.Image as _pil
_screenshot = _glob.glob("/home/wissam/projects/inf112/ziani-lahouassa-inf112/Capture*.png")[0]
_w, _h = _pil.open(_screenshot).size
_img_w = 14 * cm
_img_h = _img_w * _h / _w
img = Image(_screenshot, width=_img_w, height=_img_h)
story.append(img)

# ── 2. Résumé global ─────────────────────────────────────────────────────────
story.append(Paragraph("2. Résumé global des résultats", h1_style))
story.append(result_table([
    ["InitTest",       "3",  "0", "✔ OK"],
    ["AddMemberTest",  "15", "0", "✔ OK"],
    ["AddItemBookTest","26", "0", "✔ OK"],
    ["TOTAL",          "44", "0", "✔ OK"],
]))

doc.build(story)
print(f"PDF generated: {OUTPUT}")
