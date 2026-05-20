-- ═══════════════════════════════════════════════════════════
-- jugadores_data.sql
-- Plantillas convocadas — FIFA World Cup 2026
-- Ejecutar DESPUÉS de data.sql (equipos deben existir)
--
-- Posiciones: PORTERO | DEFENSA | MEDIOCAMPO | DELANTERO
-- esEstrella: true solo para los jugadores más reconocidos
--
-- NOTA: estos son planteles representativos basados en los
-- convocados habituales de cada selección. El admin puede
-- ajustar vía POST /api/admin/equipos/{id}/jugadores
-- ═══════════════════════════════════════════════════════════

-- ═══ GRUPO A ═══════════════════════════════════════════════

-- México
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='México'), 'Guillermo Ochoa',   'PORTERO',    1,  false),
((SELECT id FROM equipos WHERE nombre='México'), 'Luis Ángel Malagón','PORTERO',   13,  false),
((SELECT id FROM equipos WHERE nombre='México'), 'César Montes',      'DEFENSA',    3,  false),
((SELECT id FROM equipos WHERE nombre='México'), 'Edson Álvarez',     'DEFENSA',    4,  true),
((SELECT id FROM equipos WHERE nombre='México'), 'Jesús Gallardo',    'DEFENSA',    5,  false),
((SELECT id FROM equipos WHERE nombre='México'), 'Johan Vásquez',     'DEFENSA',    15, false),
((SELECT id FROM equipos WHERE nombre='México'), 'Luis Chávez',       'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='México'), 'Orbelín Pineda',    'MEDIOCAMPO', 10, false),
((SELECT id FROM equipos WHERE nombre='México'), 'Carlos Rodríguez',  'MEDIOCAMPO', 16, false),
((SELECT id FROM equipos WHERE nombre='México'), 'Hirving Lozano',    'DELANTERO',  22, true),
((SELECT id FROM equipos WHERE nombre='México'), 'Raúl Jiménez',      'DELANTERO',  9,  true),
((SELECT id FROM equipos WHERE nombre='México'), 'Henry Martín',      'DELANTERO',  7,  false),
((SELECT id FROM equipos WHERE nombre='México'), 'Santiago Giménez',  'DELANTERO',  11, true);

-- Sudáfrica
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Sudáfrica'), 'Ronwen Williams',  'PORTERO',    1,  true),
((SELECT id FROM equipos WHERE nombre='Sudáfrica'), 'Reeve Frosler',    'DEFENSA',    2,  false),
((SELECT id FROM equipos WHERE nombre='Sudáfrica'), 'Rushine de Reuck', 'DEFENSA',    5,  false),
((SELECT id FROM equipos WHERE nombre='Sudáfrica'), 'Mothobi Mvala',    'MEDIOCAMPO', 8,  false),
((SELECT id FROM equipos WHERE nombre='Sudáfrica'), 'Themba Zwane',     'MEDIOCAMPO', 10, true),
((SELECT id FROM equipos WHERE nombre='Sudáfrica'), 'Bongani Zungu',    'MEDIOCAMPO', 6,  true),
((SELECT id FROM equipos WHERE nombre='Sudáfrica'), 'Percy Tau',        'DELANTERO',  11, true),
((SELECT id FROM equipos WHERE nombre='Sudáfrica'), 'Evidence Makgopa', 'DELANTERO',  9,  false),
((SELECT id FROM equipos WHERE nombre='Sudáfrica'), 'Lyle Foster',      'DELANTERO',  19, true);

-- Corea del Sur
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Corea del Sur'), 'Kim Seung-gyu',  'PORTERO',    1,  false),
((SELECT id FROM equipos WHERE nombre='Corea del Sur'), 'Kim Min-jae',    'DEFENSA',    3,  true),
((SELECT id FROM equipos WHERE nombre='Corea del Sur'), 'Lee Ki-je',      'DEFENSA',    6,  false),
((SELECT id FROM equipos WHERE nombre='Corea del Sur'), 'Jung Woo-young', 'MEDIOCAMPO', 16, false),
((SELECT id FROM equipos WHERE nombre='Corea del Sur'), 'Lee Jae-sung',   'MEDIOCAMPO', 17, true),
((SELECT id FROM equipos WHERE nombre='Corea del Sur'), 'Hwang In-beom',  'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='Corea del Sur'), 'Son Heung-min',  'DELANTERO',  7,  true),
((SELECT id FROM equipos WHERE nombre='Corea del Sur'), 'Hwang Hee-chan', 'DELANTERO',  11, true),
((SELECT id FROM equipos WHERE nombre='Corea del Sur'), 'Cho Gue-sung',   'DELANTERO',  9,  false);

-- Chequia
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Chequia'), 'Jiří Staněk',    'PORTERO',    23, true),
((SELECT id FROM equipos WHERE nombre='Chequia'), 'Vladimír Coufal','DEFENSA',    5,  true),
((SELECT id FROM equipos WHERE nombre='Chequia'), 'Tomáš Holeš',    'DEFENSA',    16, false),
((SELECT id FROM equipos WHERE nombre='Chequia'), 'Lukáš Provod',   'MEDIOCAMPO', 8,  false),
((SELECT id FROM equipos WHERE nombre='Chequia'), 'Tomáš Souček',   'MEDIOCAMPO', 6,  true),
((SELECT id FROM equipos WHERE nombre='Chequia'), 'Alex Král',      'MEDIOCAMPO', 14, false),
((SELECT id FROM equipos WHERE nombre='Chequia'), 'Patrik Schick',  'DELANTERO',  7,  true),
((SELECT id FROM equipos WHERE nombre='Chequia'), 'Adam Hložek',    'DELANTERO',  10, true);

-- ═══ GRUPO B ═══════════════════════════════════════════════

-- Canadá
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Canadá'), 'Maxime Crépeau',   'PORTERO',    1,  false),
((SELECT id FROM equipos WHERE nombre='Canadá'), 'Alistair Johnston', 'DEFENSA',   13, true),
((SELECT id FROM equipos WHERE nombre='Canadá'), 'Kamal Miller',     'DEFENSA',    5,  false),
((SELECT id FROM equipos WHERE nombre='Canadá'), 'Scott Kennedy',    'DEFENSA',    3,  false),
((SELECT id FROM equipos WHERE nombre='Canadá'), 'Stephen Eustáquio','MEDIOCAMPO', 7,  true),
((SELECT id FROM equipos WHERE nombre='Canadá'), 'Tajon Buchanan',   'MEDIOCAMPO', 11, true),
((SELECT id FROM equipos WHERE nombre='Canadá'), 'Ismaël Koné',      'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='Canadá'), 'Alphonso Davies',  'DELANTERO',  19, true),
((SELECT id FROM equipos WHERE nombre='Canadá'), 'Jonathan David',   'DELANTERO',  9,  true),
((SELECT id FROM equipos WHERE nombre='Canadá'), 'Cyle Larin',       'DELANTERO',  10, true);

-- Suiza
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Suiza'), 'Yann Sommer',     'PORTERO',    1,  true),
((SELECT id FROM equipos WHERE nombre='Suiza'), 'Manuel Akanji',   'DEFENSA',    5,  true),
((SELECT id FROM equipos WHERE nombre='Suiza'), 'Nico Elvedi',     'DEFENSA',    22, false),
((SELECT id FROM equipos WHERE nombre='Suiza'), 'Fabian Rieder',   'MEDIOCAMPO', 8,  false),
((SELECT id FROM equipos WHERE nombre='Suiza'), 'Granit Xhaka',    'MEDIOCAMPO', 10, true),
((SELECT id FROM equipos WHERE nombre='Suiza'), 'Remo Freuler',    'MEDIOCAMPO', 13, true),
((SELECT id FROM equipos WHERE nombre='Suiza'), 'Xherdan Shaqiri', 'DELANTERO',  23, true),
((SELECT id FROM equipos WHERE nombre='Suiza'), 'Breel Embolo',    'DELANTERO',  7,  true),
((SELECT id FROM equipos WHERE nombre='Suiza'), 'Noah Okafor',     'DELANTERO',  11, false);

-- ═══ GRUPO C ═══════════════════════════════════════════════

-- Brasil
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Alisson Becker',  'PORTERO',    1,  true),
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Éderson',         'PORTERO',   23,  false),
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Danilo',          'DEFENSA',    2,  false),
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Marquinhos',      'DEFENSA',    4,  true),
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Gabriel Magalhães','DEFENSA',   3,  false),
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Renan Lodi',      'DEFENSA',    6,  false),
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Casemiro',        'MEDIOCAMPO', 5,  true),
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Lucas Paquetá',   'MEDIOCAMPO', 10, true),
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Bruno Guimarães', 'MEDIOCAMPO', 14, true),
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Rodrygo',         'DELANTERO',  11, true),
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Vinicius Jr',     'DELANTERO',  7,  true),
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Raphinha',        'DELANTERO',  22, true),
((SELECT id FROM equipos WHERE nombre='Brasil'), 'Endrick',         'DELANTERO',  9,  true);

-- Marruecos
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Marruecos'), 'Yassine Bounou',  'PORTERO',    1,  true),
((SELECT id FROM equipos WHERE nombre='Marruecos'), 'Achraf Hakimi',   'DEFENSA',    2,  true),
((SELECT id FROM equipos WHERE nombre='Marruecos'), 'Romain Saïss',    'DEFENSA',    5,  false),
((SELECT id FROM equipos WHERE nombre='Marruecos'), 'Nayef Aguerd',    'DEFENSA',    6,  false),
((SELECT id FROM equipos WHERE nombre='Marruecos'), 'Sofyan Amrabat',  'MEDIOCAMPO', 4,  true),
((SELECT id FROM equipos WHERE nombre='Marruecos'), 'Azzedine Ounahi', 'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='Marruecos'), 'Hakim Ziyech',    'MEDIOCAMPO', 7,  true),
((SELECT id FROM equipos WHERE nombre='Marruecos'), 'Youssef En-Nesyri','DELANTERO', 9,  true),
((SELECT id FROM equipos WHERE nombre='Marruecos'), 'Sofiane Boufal',  'DELANTERO',  11, false);

-- ═══ GRUPO D ═══════════════════════════════════════════════

-- Estados Unidos
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), 'Matt Turner',      'PORTERO',    1,  false),
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), 'Sergiño Dest',     'DEFENSA',    2,  true),
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), 'Walker Zimmerman', 'DEFENSA',    5,  false),
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), 'Tim Ream',         'DEFENSA',    13, false),
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), 'Antonee Robinson', 'DEFENSA',    3,  true),
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), 'Tyler Adams',      'MEDIOCAMPO', 4,  true),
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), 'Weston McKennie',  'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), 'Yunus Musah',      'MEDIOCAMPO', 6,  true),
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), 'Christian Pulisic','DELANTERO',  10, true),
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), 'Josh Sargent',     'DELANTERO',  9,  false),
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), 'Timothy Weah',     'DELANTERO',  11, true);

-- Argentina
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Emiliano Martínez', 'PORTERO',    23, true),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Franco Armani',     'PORTERO',    1,  false),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Nahuel Molina',     'DEFENSA',    26, true),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Cristian Romero',   'DEFENSA',    13, true),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Lisandro Martínez', 'DEFENSA',    14, true),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Nicolás Otamendi',  'DEFENSA',    19, false),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Nicolás Tagliafico','DEFENSA',    3,  false),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Rodrigo De Paul',   'MEDIOCAMPO', 7,  true),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Enzo Fernández',    'MEDIOCAMPO', 24, true),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Alexis Mac Allister','MEDIOCAMPO',20, true),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Lionel Messi',      'DELANTERO',  10, true),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Julián Álvarez',    'DELANTERO',  9,  true),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Lautaro Martínez',  'DELANTERO',  22, true),
((SELECT id FROM equipos WHERE nombre='Argentina'), 'Paulo Dybala',      'DELANTERO',  21, true);

-- ═══ GRUPO E ═══════════════════════════════════════════════

-- Alemania
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Alemania'), 'Manuel Neuer',       'PORTERO',    1,  true),
((SELECT id FROM equipos WHERE nombre='Alemania'), 'Marc-André ter Stegen','PORTERO',  22, false),
((SELECT id FROM equipos WHERE nombre='Alemania'), 'Joshua Kimmich',     'DEFENSA',    6,  true),
((SELECT id FROM equipos WHERE nombre='Alemania'), 'Antonio Rüdiger',    'DEFENSA',    2,  true),
((SELECT id FROM equipos WHERE nombre='Alemania'), 'Niklas Süle',        'DEFENSA',    15, false),
((SELECT id FROM equipos WHERE nombre='Alemania'), 'David Raum',         'DEFENSA',    19, false),
((SELECT id FROM equipos WHERE nombre='Alemania'), 'Ilkay Gündogan',     'MEDIOCAMPO', 21, true),
((SELECT id FROM equipos WHERE nombre='Alemania'), 'Toni Kroos',         'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='Alemania'), 'Jamal Musiala',      'MEDIOCAMPO', 10, true),
((SELECT id FROM equipos WHERE nombre='Alemania'), 'Florian Wirtz',      'MEDIOCAMPO', 17, true),
((SELECT id FROM equipos WHERE nombre='Alemania'), 'Thomas Müller',      'DELANTERO',  25, true),
((SELECT id FROM equipos WHERE nombre='Alemania'), 'Leroy Sané',         'DELANTERO',  7,  true),
((SELECT id FROM equipos WHERE nombre='Alemania'), 'Kai Havertz',        'DELANTERO',  9,  true);

-- Ecuador
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Ecuador'), 'Hernán Galíndez', 'PORTERO',    1,  false),
((SELECT id FROM equipos WHERE nombre='Ecuador'), 'Piero Hincapié',  'DEFENSA',    3,  true),
((SELECT id FROM equipos WHERE nombre='Ecuador'), 'Byron Castillo',  'DEFENSA',    2,  false),
((SELECT id FROM equipos WHERE nombre='Ecuador'), 'Willian Pacho',   'DEFENSA',    5,  true),
((SELECT id FROM equipos WHERE nombre='Ecuador'), 'Moisés Caicedo',  'MEDIOCAMPO', 6,  true),
((SELECT id FROM equipos WHERE nombre='Ecuador'), 'Carlos Gruezo',   'MEDIOCAMPO', 8,  false),
((SELECT id FROM equipos WHERE nombre='Ecuador'), 'Ángel Mena',      'DELANTERO',  7,  false),
((SELECT id FROM equipos WHERE nombre='Ecuador'), 'Enner Valencia',  'DELANTERO',  13, true),
((SELECT id FROM equipos WHERE nombre='Ecuador'), 'Gonzalo Plata',   'DELANTERO',  11, true);

-- ═══ GRUPO F ═══════════════════════════════════════════════

-- Países Bajos
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Países Bajos'), 'Bart Verbruggen', 'PORTERO',    1,  false),
((SELECT id FROM equipos WHERE nombre='Países Bajos'), 'Denzel Dumfries', 'DEFENSA',    22, true),
((SELECT id FROM equipos WHERE nombre='Países Bajos'), 'Virgil van Dijk', 'DEFENSA',    4,  true),
((SELECT id FROM equipos WHERE nombre='Países Bajos'), 'Matthijs de Ligt','DEFENSA',    3,  true),
((SELECT id FROM equipos WHERE nombre='Países Bajos'), 'Nathan Aké',      'DEFENSA',    5,  false),
((SELECT id FROM equipos WHERE nombre='Países Bajos'), 'Frenkie de Jong', 'MEDIOCAMPO', 21, true),
((SELECT id FROM equipos WHERE nombre='Países Bajos'), 'Tijjani Reijnders','MEDIOCAMPO',8,  true),
((SELECT id FROM equipos WHERE nombre='Países Bajos'), 'Xavi Simons',     'MEDIOCAMPO', 9,  true),
((SELECT id FROM equipos WHERE nombre='Países Bajos'), 'Cody Gakpo',      'DELANTERO',  11, true),
((SELECT id FROM equipos WHERE nombre='Países Bajos'), 'Memphis Depay',   'DELANTERO',  10, true),
((SELECT id FROM equipos WHERE nombre='Países Bajos'), 'Donyell Malen',   'DELANTERO',  7,  false);

-- Japón
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Japón'), 'Shuichi Gonda',    'PORTERO',    12, false),
((SELECT id FROM equipos WHERE nombre='Japón'), 'Maya Yoshida',     'DEFENSA',    22, false),
((SELECT id FROM equipos WHERE nombre='Japón'), 'Ko Itakura',       'DEFENSA',    4,  true),
((SELECT id FROM equipos WHERE nombre='Japón'), 'Yuto Nagatomo',    'DEFENSA',    5,  false),
((SELECT id FROM equipos WHERE nombre='Japón'), 'Hidemasa Morita',  'MEDIOCAMPO', 6,  false),
((SELECT id FROM equipos WHERE nombre='Japón'), 'Wataru Endō',      'MEDIOCAMPO', 7,  true),
((SELECT id FROM equipos WHERE nombre='Japón'), 'Junya Ito',        'MEDIOCAMPO', 9,  true),
((SELECT id FROM equipos WHERE nombre='Japón'), 'Takumi Minamino',  'DELANTERO',  10, true),
((SELECT id FROM equipos WHERE nombre='Japón'), 'Ayase Ueda',       'DELANTERO',  13, true),
((SELECT id FROM equipos WHERE nombre='Japón'), 'Kaoru Mitoma',     'DELANTERO',  11, true);

-- ═══ GRUPO G ═══════════════════════════════════════════════

-- Bélgica
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Bélgica'), 'Koen Casteels',    'PORTERO',    1,  true),
((SELECT id FROM equipos WHERE nombre='Bélgica'), 'Timothy Castagne', 'DEFENSA',    2,  false),
((SELECT id FROM equipos WHERE nombre='Bélgica'), 'Toby Alderweireld','DEFENSA',    4,  false),
((SELECT id FROM equipos WHERE nombre='Bélgica'), 'Axel Witsel',      'MEDIOCAMPO', 6,  false),
((SELECT id FROM equipos WHERE nombre='Bélgica'), 'Kevin De Bruyne',  'MEDIOCAMPO', 7,  true),
((SELECT id FROM equipos WHERE nombre='Bélgica'), 'Youri Tielemans',  'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='Bélgica'), 'Dodi Lukébakio',   'MEDIOCAMPO', 11, false),
((SELECT id FROM equipos WHERE nombre='Bélgica'), 'Romelu Lukaku',    'DELANTERO',  9,  true),
((SELECT id FROM equipos WHERE nombre='Bélgica'), 'Lois Openda',      'DELANTERO',  10, true),
((SELECT id FROM equipos WHERE nombre='Bélgica'), 'Johan Bakayoko',   'DELANTERO',  16, false);

-- ═══ GRUPO H ═══════════════════════════════════════════════

-- España
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='España'), 'Unai Simón',      'PORTERO',    1,  true),
((SELECT id FROM equipos WHERE nombre='España'), 'Dani Carvajal',   'DEFENSA',    2,  true),
((SELECT id FROM equipos WHERE nombre='España'), 'Aymeric Laporte', 'DEFENSA',    14, false),
((SELECT id FROM equipos WHERE nombre='España'), 'Robin Le Normand','DEFENSA',    24, false),
((SELECT id FROM equipos WHERE nombre='España'), 'Alejandro Balde', 'DEFENSA',    3,  true),
((SELECT id FROM equipos WHERE nombre='España'), 'Rodri',           'MEDIOCAMPO', 16, true),
((SELECT id FROM equipos WHERE nombre='España'), 'Pedri',           'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='España'), 'Fabián Ruiz',     'MEDIOCAMPO', 7,  true),
((SELECT id FROM equipos WHERE nombre='España'), 'Lamine Yamal',    'DELANTERO',  19, true),
((SELECT id FROM equipos WHERE nombre='España'), 'Nico Williams',   'DELANTERO',  11, true),
((SELECT id FROM equipos WHERE nombre='España'), 'Álvaro Morata',   'DELANTERO',  9,  true),
((SELECT id FROM equipos WHERE nombre='España'), 'Dani Olmo',       'DELANTERO',  10, true);

-- Uruguay
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Uruguay'), 'Sergio Rochet',   'PORTERO',    1,  false),
((SELECT id FROM equipos WHERE nombre='Uruguay'), 'José María Giménez','DEFENSA',  2,  true),
((SELECT id FROM equipos WHERE nombre='Uruguay'), 'Ronald Araújo',   'DEFENSA',    4,  true),
((SELECT id FROM equipos WHERE nombre='Uruguay'), 'Mathías Olivera', 'DEFENSA',    18, false),
((SELECT id FROM equipos WHERE nombre='Uruguay'), 'Fede Valverde',   'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='Uruguay'), 'Rodrigo Bentancur','MEDIOCAMPO',5,  true),
((SELECT id FROM equipos WHERE nombre='Uruguay'), 'Matías Vecino',   'MEDIOCAMPO', 15, false),
((SELECT id FROM equipos WHERE nombre='Uruguay'), 'Luis Suárez',     'DELANTERO',  9,  true),
((SELECT id FROM equipos WHERE nombre='Uruguay'), 'Darwin Núñez',    'DELANTERO',  11, true),
((SELECT id FROM equipos WHERE nombre='Uruguay'), 'Facundo Pellistri','DELANTERO', 23, false);

-- ═══ GRUPO I ═══════════════════════════════════════════════

-- Francia
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Francia'), 'Mike Maignan',     'PORTERO',    16, true),
((SELECT id FROM equipos WHERE nombre='Francia'), 'Jules Koundé',     'DEFENSA',    5,  true),
((SELECT id FROM equipos WHERE nombre='Francia'), 'Dayot Upamecano',  'DEFENSA',    4,  true),
((SELECT id FROM equipos WHERE nombre='Francia'), 'Théo Hernández',   'DEFENSA',    22, true),
((SELECT id FROM equipos WHERE nombre='Francia'), 'William Saliba',   'DEFENSA',    17, false),
((SELECT id FROM equipos WHERE nombre='Francia'), 'Aurélien Tchouaméni','MEDIOCAMPO',8, true),
((SELECT id FROM equipos WHERE nombre='Francia'), 'Adrien Rabiot',    'MEDIOCAMPO', 14, false),
((SELECT id FROM equipos WHERE nombre='Francia'), 'Antoine Griezmann','MEDIOCAMPO', 7,  true),
((SELECT id FROM equipos WHERE nombre='Francia'), 'Kylian Mbappé',    'DELANTERO',  10, true),
((SELECT id FROM equipos WHERE nombre='Francia'), 'Ousmane Dembélé',  'DELANTERO',  11, true),
((SELECT id FROM equipos WHERE nombre='Francia'), 'Marcus Thuram',    'DELANTERO',  9,  true),
((SELECT id FROM equipos WHERE nombre='Francia'), 'Bradley Barcola',  'DELANTERO',  20, true);

-- Noruega
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Noruega'), 'Ørjan Nyland',     'PORTERO',    12, false),
((SELECT id FROM equipos WHERE nombre='Noruega'), 'Kristian Thorstvedt','MEDIOCAMPO',15, true),
((SELECT id FROM equipos WHERE nombre='Noruega'), 'Sander Berge',     'MEDIOCAMPO', 6,  true),
((SELECT id FROM equipos WHERE nombre='Noruega'), 'Martin Ødegaard',  'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='Noruega'), 'Leo Østigård',     'DEFENSA',    5,  false),
((SELECT id FROM equipos WHERE nombre='Noruega'), 'Alexander Sørloth','DELANTERO',  9,  true),
((SELECT id FROM equipos WHERE nombre='Noruega'), 'Erling Haaland',   'DELANTERO',  23, true);

-- ═══ GRUPO J ═══════════════════════════════════════════════
-- (Argentina ya cargada arriba)

-- Austria
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Austria'), 'Patrick Pentz',    'PORTERO',    1,  false),
((SELECT id FROM equipos WHERE nombre='Austria'), 'Stefan Posch',     'DEFENSA',    5,  false),
((SELECT id FROM equipos WHERE nombre='Austria'), 'Philipp Mwene',    'DEFENSA',    3,  false),
((SELECT id FROM equipos WHERE nombre='Austria'), 'Nicolas Seiwald',  'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='Austria'), 'Konrad Laimer',    'MEDIOCAMPO', 7,  true),
((SELECT id FROM equipos WHERE nombre='Austria'), 'Marcel Sabitzer',  'MEDIOCAMPO', 11, true),
((SELECT id FROM equipos WHERE nombre='Austria'), 'Marko Arnautović', 'DELANTERO',  9,  true),
((SELECT id FROM equipos WHERE nombre='Austria'), 'Michael Gregoritsch','DELANTERO',17, false);

-- ═══ GRUPO K ═══════════════════════════════════════════════

-- Portugal
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Portugal'), 'Diogo Costa',      'PORTERO',    1,  true),
((SELECT id FROM equipos WHERE nombre='Portugal'), 'Diogo Dalot',      'DEFENSA',    20, true),
((SELECT id FROM equipos WHERE nombre='Portugal'), 'Rúben Dias',       'DEFENSA',    4,  true),
((SELECT id FROM equipos WHERE nombre='Portugal'), 'Pepe',             'DEFENSA',    3,  false),
((SELECT id FROM equipos WHERE nombre='Portugal'), 'Nuno Mendes',      'DEFENSA',    19, true),
((SELECT id FROM equipos WHERE nombre='Portugal'), 'João Palhinha',    'MEDIOCAMPO', 26, true),
((SELECT id FROM equipos WHERE nombre='Portugal'), 'Bruno Fernandes',  'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='Portugal'), 'Vitinha',          'MEDIOCAMPO', 16, true),
((SELECT id FROM equipos WHERE nombre='Portugal'), 'Bernardo Silva',   'MEDIOCAMPO', 10, true),
((SELECT id FROM equipos WHERE nombre='Portugal'), 'Cristiano Ronaldo','DELANTERO',  7,  true),
((SELECT id FROM equipos WHERE nombre='Portugal'), 'Rafael Leão',      'DELANTERO',  11, true),
((SELECT id FROM equipos WHERE nombre='Portugal'), 'João Félix',       'DELANTERO',  22, true),
((SELECT id FROM equipos WHERE nombre='Portugal'), 'Gonçalo Ramos',    'DELANTERO',  9,  true);

-- Colombia
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Colombia'), 'Camilo Vargas',    'PORTERO',    1,  false),
((SELECT id FROM equipos WHERE nombre='Colombia'), 'Daniel Muñoz',     'DEFENSA',    2,  false),
((SELECT id FROM equipos WHERE nombre='Colombia'), 'Davinson Sánchez', 'DEFENSA',    12, true),
((SELECT id FROM equipos WHERE nombre='Colombia'), 'Carlos Cuesta',    'DEFENSA',    3,  false),
((SELECT id FROM equipos WHERE nombre='Colombia'), 'Johan Mojica',     'DEFENSA',    16, false),
((SELECT id FROM equipos WHERE nombre='Colombia'), 'Mateus Uribe',     'MEDIOCAMPO', 8,  false),
((SELECT id FROM equipos WHERE nombre='Colombia'), 'Richard Ríos',     'MEDIOCAMPO', 17, true),
((SELECT id FROM equipos WHERE nombre='Colombia'), 'James Rodríguez',  'MEDIOCAMPO', 10, true),
((SELECT id FROM equipos WHERE nombre='Colombia'), 'Jhon Arias',       'DELANTERO',  7,  true),
((SELECT id FROM equipos WHERE nombre='Colombia'), 'Luis Díaz',        'DELANTERO',  23, true),
((SELECT id FROM equipos WHERE nombre='Colombia'), 'Rafael Santos Borré','DELANTERO',19, false),
((SELECT id FROM equipos WHERE nombre='Colombia'), 'Jhon Córdoba',     'DELANTERO',  9,  false);

-- ═══ GRUPO L ═══════════════════════════════════════════════

-- Inglaterra
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Inglaterra'), 'Jordan Pickford',  'PORTERO',    1,  true),
((SELECT id FROM equipos WHERE nombre='Inglaterra'), 'Kyle Walker',      'DEFENSA',    2,  true),
((SELECT id FROM equipos WHERE nombre='Inglaterra'), 'Harry Maguire',    'DEFENSA',    6,  false),
((SELECT id FROM equipos WHERE nombre='Inglaterra'), 'John Stones',      'DEFENSA',    5,  true),
((SELECT id FROM equipos WHERE nombre='Inglaterra'), 'Luke Shaw',        'DEFENSA',    3,  false),
((SELECT id FROM equipos WHERE nombre='Inglaterra'), 'Declan Rice',      'MEDIOCAMPO', 4,  true),
((SELECT id FROM equipos WHERE nombre='Inglaterra'), 'Jude Bellingham',  'MEDIOCAMPO', 10, true),
((SELECT id FROM equipos WHERE nombre='Inglaterra'), 'Phil Foden',       'MEDIOCAMPO', 20, true),
((SELECT id FROM equipos WHERE nombre='Inglaterra'), 'Bukayo Saka',      'DELANTERO',  7,  true),
((SELECT id FROM equipos WHERE nombre='Inglaterra'), 'Harry Kane',       'DELANTERO',  9,  true),
((SELECT id FROM equipos WHERE nombre='Inglaterra'), 'Marcus Rashford',  'DELANTERO',  11, true),
((SELECT id FROM equipos WHERE nombre='Inglaterra'), 'Cole Palmer',      'DELANTERO',  22, true);

-- Croacia
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Croacia'), 'Dominik Livaković', 'PORTERO',    1,  true),
((SELECT id FROM equipos WHERE nombre='Croacia'), 'Josip Juranović',   'DEFENSA',    2,  false),
((SELECT id FROM equipos WHERE nombre='Croacia'), 'Dejan Lovren',      'DEFENSA',    6,  false),
((SELECT id FROM equipos WHERE nombre='Croacia'), 'Joško Gvardiol',    'DEFENSA',    24, true),
((SELECT id FROM equipos WHERE nombre='Croacia'), 'Ivan Perišić',      'MEDIOCAMPO', 4,  true),
((SELECT id FROM equipos WHERE nombre='Croacia'), 'Mateo Kovačić',     'MEDIOCAMPO', 8,  true),
((SELECT id FROM equipos WHERE nombre='Croacia'), 'Luka Modrić',       'MEDIOCAMPO', 10, true),
((SELECT id FROM equipos WHERE nombre='Croacia'), 'Marcelo Brozović',  'MEDIOCAMPO', 11, true),
((SELECT id FROM equipos WHERE nombre='Croacia'), 'Andrej Kramarić',   'DELANTERO',  9,  true),
((SELECT id FROM equipos WHERE nombre='Croacia'), 'Bruno Petković',    'DELANTERO',  16, false);

-- Ghana
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Ghana'), 'Lawrence Ati-Zigi', 'PORTERO',    1,  false),
((SELECT id FROM equipos WHERE nombre='Ghana'), 'Tariq Lamptey',     'DEFENSA',    2,  true),
((SELECT id FROM equipos WHERE nombre='Ghana'), 'Alexander Djiku',   'DEFENSA',    5,  false),
((SELECT id FROM equipos WHERE nombre='Ghana'), 'Daniel Amartey',    'DEFENSA',    20, false),
((SELECT id FROM equipos WHERE nombre='Ghana'), 'Thomas Partey',     'MEDIOCAMPO', 5,  true),
((SELECT id FROM equipos WHERE nombre='Ghana'), 'Mohammed Kudus',    'MEDIOCAMPO', 10, true),
((SELECT id FROM equipos WHERE nombre='Ghana'), 'André Ayew',        'MEDIOCAMPO', 11, false),
((SELECT id FROM equipos WHERE nombre='Ghana'), 'Jordan Ayew',       'DELANTERO',  9,  false),
((SELECT id FROM equipos WHERE nombre='Ghana'), 'Antoine Semenyo',   'DELANTERO',  19, true),
((SELECT id FROM equipos WHERE nombre='Ghana'), 'Inaki Williams',    'DELANTERO',  7,  true);

-- Panamá
INSERT INTO jugadores (equipo_id, nombre, posicion, nro_camiseta, es_estrella) VALUES
((SELECT id FROM equipos WHERE nombre='Panamá'), 'Luis Mejía',        'PORTERO',    1,  false),
((SELECT id FROM equipos WHERE nombre='Panamá'), 'Fidel Escobar',     'DEFENSA',    4,  false),
((SELECT id FROM equipos WHERE nombre='Panamá'), 'Harold Cummings',   'DEFENSA',    5,  false),
((SELECT id FROM equipos WHERE nombre='Panamá'), 'Roderick Miller',   'MEDIOCAMPO', 17, false),
((SELECT id FROM equipos WHERE nombre='Panamá'), 'Anibal Godoy',      'MEDIOCAMPO', 8,  false),
((SELECT id FROM equipos WHERE nombre='Panamá'), 'Adalberto Carrasquilla','MEDIOCAMPO',10,true),
((SELECT id FROM equipos WHERE nombre='Panamá'), 'Ismael Díaz',       'DELANTERO',  7,  true),
((SELECT id FROM equipos WHERE nombre='Panamá'), 'Rolando Blackburn', 'DELANTERO',  9,  false);

-- Estadísticas iniciales (rank FIFA pre-torneo, títulos mundiales)
-- El admin puede actualizar estos valores en la base de datos directamente
UPDATE equipos e
SET id = id  -- placeholder para triggering del siguiente bloque
WHERE id IS NOT NULL;

INSERT INTO estadisticas_equipo (equipo_id, rank_fifa, titulos_mundiales, pj, pg, pe, pp, gf, gc)
SELECT e.id, r.rank_fifa, r.titulos, 0, 0, 0, 0, 0, 0
FROM equipos e
JOIN (VALUES
  ('Argentina',    1,  3),
  ('Francia',      2,  2),
  ('Inglaterra',   3,  1),
  ('Brasil',       5,  5),
  ('Bélgica',      3,  0),
  ('Portugal',     6,  0),
  ('Países Bajos', 7,  0),
  ('España',       8,  4),
  ('Alemania',     16, 4),
  ('Uruguay',      17, 2),
  ('Croacia',      10, 0),
  ('Estados Unidos',13,0),
  ('Japón',        18, 0),
  ('Marruecos',    14, 0),
  ('Suiza',        19, 0),
  ('México',       15, 0),
  ('Colombia',     11, 0),
  ('Corea del Sur',23, 0),
  ('Noruega',      20, 0),
  ('Austria',      25, 0),
  ('Canadá',       40, 0),
  ('Ecuador',      39, 0),
  ('Suecia',       24, 0),
  ('Ghana',        60, 0),
  ('Senegal',      21, 0),
  ('Irán',         22, 0),
  ('Chequia',      37, 0),
  ('Túnez',        29, 0),
  ('Irak',         55, 0),
  ('Sudáfrica',    63, 0),
  ('Egipto',       35, 0),
  ('Australia',    23, 0),
  ('Türkiye',      38, 0),
  ('Uzbekistán',   73, 0),
  ('Argelia',      33, 0),
  ('Jordania',     87, 0),
  ('Paraguay',     60, 0),
  ('Arabia Saudita',59,0),
  ('Nueva Zelanda',102,0),
  ('Haití',        83, 0),
  ('Escocia',      45, 0),
  ('Cabo Verde',   89, 0),
  ('Bosnia-Herzegovina',53,0),
  ('Catar',        58, 0),
  ('Curazao',      88, 0),
  ('Costa de Marfil',50,0),
  ('Panamá',       76, 0),
  ('Congo DR',     99, 0)
) AS r(nombre, rank_fifa, titulos) ON e.nombre = r.nombre
ON CONFLICT (equipo_id) DO NOTHING;
