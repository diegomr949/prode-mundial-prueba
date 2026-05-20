-- ═══════════════════════════════════════════════════════════
-- data.sql — Datos iniciales del sistema
-- CPCE Mendoza · Prode Mundial 2026
--
-- Spring Boot lo ejecuta automáticamente al arrancar
-- cuando sql.init.mode=always en application.yml.
--
-- IMPORTANTE: Cambiar a sql.init.mode=never después del
-- primer deploy para evitar re-ejecución en cada restart.
-- ═══════════════════════════════════════════════════════════

-- ── Usuario administrador ────────────────────────────────
-- Contraseña: Admin2026!
-- Hash BCrypt strength 12 — cambiar en producción
-- Generar nuevo hash: https://bcrypt-generator.com (rounds: 12)
-- Usuario: Federico Cuervo
INSERT INTO usuarios (
    email, password_hash, nombre,
    puntos_totales, plenos_totales, rol, fecha_registro
) VALUES (
    'fcuervo@cpcemza.org.ar',
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TgxwIuGQAfhCdUMvHHi2tJwF4Lme', -- password123
    'Federico Cuervo',
    0, 0, 'ROLE_USER', NOW()
) ON CONFLICT (email) DO NOTHING;

-- Usuario: Diego Moreno
INSERT INTO usuarios (
    email, password_hash, nombre,
    puntos_totales, plenos_totales, rol, fecha_registro
) VALUES (
    'dmoreno@cpcemza.org.ar',
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TgxwIuGQAfhCdUMvHHi2tJwF4Lme', -- password123
    'Diego Moreno',
    0, 0, 'ROLE_USER', NOW()
) ON CONFLICT (email) DO NOTHING;

-- Usuario: Juan Francisco Vazquez
INSERT INTO usuarios (
    email, password_hash, nombre,
    puntos_totales, plenos_totales, rol, fecha_registro
) VALUES (
    'jfvazquez@cpcemza.org.ar',
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TgxwIuGQAfhCdUMvHHi2tJwF4Lme', -- password123
    'Juan Francisco Vazquez',
    0, 0, 'ROLE_USER', NOW()
) ON CONFLICT (email) DO NOTHING;

-- Usuario: Javier Lemos
INSERT INTO usuarios (
    email, password_hash, nombre,
    puntos_totales, plenos_totales, rol, fecha_registro
) VALUES (
    'jlemos@cpcemza.org.ar',
    '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TgxwIuGQAfhCdUMvHHi2tJwF4Lme', -- password123
    'Javier Lemos',
    0, 0, 'ROLE_USER', NOW()
) ON CONFLICT (email) DO NOTHING;

-- ── Equipos — 48 selecciones (12 grupos) ─────────────────
INSERT INTO equipos (nombre, grupo, bandera_url) VALUES
('México',              'A', 'https://flagcdn.com/w40/mx.png'),
('Sudáfrica',           'A', 'https://flagcdn.com/w40/za.png'),
('Corea del Sur',       'A', 'https://flagcdn.com/w40/kr.png'),
('Chequia',             'A', 'https://flagcdn.com/w40/cz.png'),
('Canadá',              'B', 'https://flagcdn.com/w40/ca.png'),
('Bosnia-Herzegovina',  'B', 'https://flagcdn.com/w40/ba.png'),
('Catar',               'B', 'https://flagcdn.com/w40/qa.png'),
('Suiza',               'B', 'https://flagcdn.com/w40/ch.png'),
('Brasil',              'C', 'https://flagcdn.com/w40/br.png'),
('Marruecos',           'C', 'https://flagcdn.com/w40/ma.png'),
('Haití',               'C', 'https://flagcdn.com/w40/ht.png'),
('Escocia',             'C', 'https://flagcdn.com/w40/gb-sct.png'),
('Estados Unidos',      'D', 'https://flagcdn.com/w40/us.png'),
('Paraguay',            'D', 'https://flagcdn.com/w40/py.png'),
('Australia',           'D', 'https://flagcdn.com/w40/au.png'),
('Türkiye',             'D', 'https://flagcdn.com/w40/tr.png'),
('Alemania',            'E', 'https://flagcdn.com/w40/de.png'),
('Curazao',             'E', 'https://flagcdn.com/w40/cw.png'),
('Costa de Marfil',     'E', 'https://flagcdn.com/w40/ci.png'),
('Ecuador',             'E', 'https://flagcdn.com/w40/ec.png'),
('Países Bajos',        'F', 'https://flagcdn.com/w40/nl.png'),
('Japón',               'F', 'https://flagcdn.com/w40/jp.png'),
('Suecia',              'F', 'https://flagcdn.com/w40/se.png'),
('Túnez',               'F', 'https://flagcdn.com/w40/tn.png'),
('Bélgica',             'G', 'https://flagcdn.com/w40/be.png'),
('Egipto',              'G', 'https://flagcdn.com/w40/eg.png'),
('Irán',                'G', 'https://flagcdn.com/w40/ir.png'),
('Nueva Zelanda',       'G', 'https://flagcdn.com/w40/nz.png'),
('España',              'H', 'https://flagcdn.com/w40/es.png'),
('Cabo Verde',          'H', 'https://flagcdn.com/w40/cv.png'),
('Arabia Saudita',      'H', 'https://flagcdn.com/w40/sa.png'),
('Uruguay',             'H', 'https://flagcdn.com/w40/uy.png'),
('Francia',             'I', 'https://flagcdn.com/w40/fr.png'),
('Senegal',             'I', 'https://flagcdn.com/w40/sn.png'),
('Irak',                'I', 'https://flagcdn.com/w40/iq.png'),
('Noruega',             'I', 'https://flagcdn.com/w40/no.png'),
('Argentina',           'J', 'https://flagcdn.com/w40/ar.png'),
('Argelia',             'J', 'https://flagcdn.com/w40/dz.png'),
('Austria',             'J', 'https://flagcdn.com/w40/at.png'),
('Jordania',            'J', 'https://flagcdn.com/w40/jo.png'),
('Portugal',            'K', 'https://flagcdn.com/w40/pt.png'),
('Congo DR',            'K', 'https://flagcdn.com/w40/cd.png'),
('Uzbekistán',          'K', 'https://flagcdn.com/w40/uz.png'),
('Colombia',            'K', 'https://flagcdn.com/w40/co.png'),
('Inglaterra',          'L', 'https://flagcdn.com/w40/gb-eng.png'),
('Croacia',             'L', 'https://flagcdn.com/w40/hr.png'),
('Ghana',               'L', 'https://flagcdn.com/w40/gh.png'),
('Panamá',              'L', 'https://flagcdn.com/w40/pa.png')
ON CONFLICT (nombre) DO NOTHING;

-- ── Partidos — Fase de Grupos (48 partidos) ───────────────
-- Horarios en UTC-3 (Buenos Aires / Argentina)

-- JORNADA 1 ──────────────────────────────────────────────
INSERT INTO partidos (equipo_local_id, equipo_visitante_id, fecha_hora, estado) VALUES
((SELECT id FROM equipos WHERE nombre='México'),         (SELECT id FROM equipos WHERE nombre='Sudáfrica'),       '2026-06-11 16:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Corea del Sur'),  (SELECT id FROM equipos WHERE nombre='Chequia'),         '2026-06-11 23:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Canadá'),         (SELECT id FROM equipos WHERE nombre='Bosnia-Herzegovina'),'2026-06-12 16:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), (SELECT id FROM equipos WHERE nombre='Paraguay'),        '2026-06-12 22:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Catar'),          (SELECT id FROM equipos WHERE nombre='Suiza'),           '2026-06-13 16:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Brasil'),         (SELECT id FROM equipos WHERE nombre='Marruecos'),       '2026-06-13 19:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Haití'),          (SELECT id FROM equipos WHERE nombre='Escocia'),         '2026-06-13 22:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Australia'),      (SELECT id FROM equipos WHERE nombre='Türkiye'),         '2026-06-14 01:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Alemania'),       (SELECT id FROM equipos WHERE nombre='Curazao'),         '2026-06-14 14:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Países Bajos'),   (SELECT id FROM equipos WHERE nombre='Japón'),           '2026-06-14 17:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Costa de Marfil'),(SELECT id FROM equipos WHERE nombre='Ecuador'),         '2026-06-14 20:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Suecia'),         (SELECT id FROM equipos WHERE nombre='Túnez'),           '2026-06-14 23:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='España'),         (SELECT id FROM equipos WHERE nombre='Cabo Verde'),      '2026-06-15 13:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Bélgica'),        (SELECT id FROM equipos WHERE nombre='Egipto'),          '2026-06-15 16:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Arabia Saudita'), (SELECT id FROM equipos WHERE nombre='Uruguay'),         '2026-06-15 19:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Irán'),           (SELECT id FROM equipos WHERE nombre='Nueva Zelanda'),   '2026-06-15 22:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Francia'),        (SELECT id FROM equipos WHERE nombre='Senegal'),         '2026-06-16 16:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Irak'),           (SELECT id FROM equipos WHERE nombre='Noruega'),         '2026-06-16 19:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Argentina'),      (SELECT id FROM equipos WHERE nombre='Argelia'),         '2026-06-16 22:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Austria'),        (SELECT id FROM equipos WHERE nombre='Jordania'),        '2026-06-17 01:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Portugal'),       (SELECT id FROM equipos WHERE nombre='Congo DR'),        '2026-06-17 14:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Inglaterra'),     (SELECT id FROM equipos WHERE nombre='Croacia'),         '2026-06-17 17:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Ghana'),          (SELECT id FROM equipos WHERE nombre='Panamá'),          '2026-06-17 20:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Uzbekistán'),     (SELECT id FROM equipos WHERE nombre='Colombia'),        '2026-06-17 23:00:00','PENDIENTE')
ON CONFLICT DO NOTHING;

-- JORNADA 2 ──────────────────────────────────────────────
INSERT INTO partidos (equipo_local_id, equipo_visitante_id, fecha_hora, estado) VALUES
((SELECT id FROM equipos WHERE nombre='Chequia'),        (SELECT id FROM equipos WHERE nombre='Sudáfrica'),       '2026-06-18 13:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Suiza'),          (SELECT id FROM equipos WHERE nombre='Bosnia-Herzegovina'),'2026-06-18 16:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Canadá'),         (SELECT id FROM equipos WHERE nombre='Catar'),           '2026-06-18 19:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='México'),         (SELECT id FROM equipos WHERE nombre='Corea del Sur'),   '2026-06-18 22:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Estados Unidos'), (SELECT id FROM equipos WHERE nombre='Australia'),       '2026-06-19 16:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Escocia'),        (SELECT id FROM equipos WHERE nombre='Marruecos'),       '2026-06-19 19:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Brasil'),         (SELECT id FROM equipos WHERE nombre='Haití'),           '2026-06-19 21:30:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Türkiye'),        (SELECT id FROM equipos WHERE nombre='Paraguay'),        '2026-06-20 00:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Países Bajos'),   (SELECT id FROM equipos WHERE nombre='Suecia'),          '2026-06-20 14:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Alemania'),       (SELECT id FROM equipos WHERE nombre='Costa de Marfil'), '2026-06-20 17:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Ecuador'),        (SELECT id FROM equipos WHERE nombre='Curazao'),         '2026-06-20 21:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Túnez'),          (SELECT id FROM equipos WHERE nombre='Japón'),           '2026-06-21 01:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='España'),         (SELECT id FROM equipos WHERE nombre='Arabia Saudita'),  '2026-06-21 13:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Bélgica'),        (SELECT id FROM equipos WHERE nombre='Irán'),            '2026-06-21 16:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Uruguay'),        (SELECT id FROM equipos WHERE nombre='Cabo Verde'),      '2026-06-21 19:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Nueva Zelanda'),  (SELECT id FROM equipos WHERE nombre='Egipto'),          '2026-06-21 22:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Argentina'),      (SELECT id FROM equipos WHERE nombre='Austria'),         '2026-06-22 14:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Francia'),        (SELECT id FROM equipos WHERE nombre='Irak'),            '2026-06-22 18:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Noruega'),        (SELECT id FROM equipos WHERE nombre='Senegal'),         '2026-06-22 21:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Jordania'),       (SELECT id FROM equipos WHERE nombre='Argelia'),         '2026-06-23 00:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Portugal'),       (SELECT id FROM equipos WHERE nombre='Uzbekistán'),      '2026-06-23 14:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Inglaterra'),     (SELECT id FROM equipos WHERE nombre='Ghana'),           '2026-06-23 17:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Panamá'),         (SELECT id FROM equipos WHERE nombre='Croacia'),         '2026-06-23 20:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Colombia'),       (SELECT id FROM equipos WHERE nombre='Congo DR'),        '2026-06-23 23:00:00','PENDIENTE')
ON CONFLICT DO NOTHING;

-- JORNADA 3 — Simultáneos por grupo ─────────────────────
INSERT INTO partidos (equipo_local_id, equipo_visitante_id, fecha_hora, estado) VALUES
-- Grupo B
((SELECT id FROM equipos WHERE nombre='Suiza'),          (SELECT id FROM equipos WHERE nombre='Canadá'),          '2026-06-24 16:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Bosnia-Herzegovina'),(SELECT id FROM equipos WHERE nombre='Catar'),         '2026-06-24 16:00:00','PENDIENTE'),
-- Grupo C
((SELECT id FROM equipos WHERE nombre='Escocia'),        (SELECT id FROM equipos WHERE nombre='Brasil'),          '2026-06-24 19:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Marruecos'),      (SELECT id FROM equipos WHERE nombre='Haití'),           '2026-06-24 19:00:00','PENDIENTE'),
-- Grupo A
((SELECT id FROM equipos WHERE nombre='Chequia'),        (SELECT id FROM equipos WHERE nombre='México'),          '2026-06-24 22:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Sudáfrica'),      (SELECT id FROM equipos WHERE nombre='Corea del Sur'),   '2026-06-24 22:00:00','PENDIENTE'),
-- Grupo E
((SELECT id FROM equipos WHERE nombre='Curazao'),        (SELECT id FROM equipos WHERE nombre='Costa de Marfil'), '2026-06-25 17:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Ecuador'),        (SELECT id FROM equipos WHERE nombre='Alemania'),        '2026-06-25 17:00:00','PENDIENTE'),
-- Grupo F
((SELECT id FROM equipos WHERE nombre='Japón'),          (SELECT id FROM equipos WHERE nombre='Suecia'),          '2026-06-25 20:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Túnez'),          (SELECT id FROM equipos WHERE nombre='Países Bajos'),    '2026-06-25 20:00:00','PENDIENTE'),
-- Grupo D
((SELECT id FROM equipos WHERE nombre='Türkiye'),        (SELECT id FROM equipos WHERE nombre='Estados Unidos'),  '2026-06-25 23:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Paraguay'),       (SELECT id FROM equipos WHERE nombre='Australia'),       '2026-06-25 23:00:00','PENDIENTE'),
-- Grupo I
((SELECT id FROM equipos WHERE nombre='Noruega'),        (SELECT id FROM equipos WHERE nombre='Francia'),         '2026-06-26 16:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Senegal'),        (SELECT id FROM equipos WHERE nombre='Irak'),            '2026-06-26 16:00:00','PENDIENTE'),
-- Grupo H
((SELECT id FROM equipos WHERE nombre='Cabo Verde'),     (SELECT id FROM equipos WHERE nombre='Arabia Saudita'),  '2026-06-26 21:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Uruguay'),        (SELECT id FROM equipos WHERE nombre='España'),          '2026-06-26 21:00:00','PENDIENTE'),
-- Grupo G
((SELECT id FROM equipos WHERE nombre='Egipto'),         (SELECT id FROM equipos WHERE nombre='Irán'),            '2026-06-27 00:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Nueva Zelanda'),  (SELECT id FROM equipos WHERE nombre='Bélgica'),         '2026-06-27 00:00:00','PENDIENTE'),
-- Grupo L
((SELECT id FROM equipos WHERE nombre='Panamá'),         (SELECT id FROM equipos WHERE nombre='Inglaterra'),      '2026-06-27 18:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Croacia'),        (SELECT id FROM equipos WHERE nombre='Ghana'),           '2026-06-27 18:00:00','PENDIENTE'),
-- Grupo K
((SELECT id FROM equipos WHERE nombre='Colombia'),       (SELECT id FROM equipos WHERE nombre='Portugal'),        '2026-06-27 20:30:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Congo DR'),       (SELECT id FROM equipos WHERE nombre='Uzbekistán'),      '2026-06-27 20:30:00','PENDIENTE'),
-- Grupo J
((SELECT id FROM equipos WHERE nombre='Argelia'),        (SELECT id FROM equipos WHERE nombre='Austria'),         '2026-06-27 23:00:00','PENDIENTE'),
((SELECT id FROM equipos WHERE nombre='Jordania'),       (SELECT id FROM equipos WHERE nombre='Argentina'),       '2026-06-27 23:00:00','PENDIENTE')
ON CONFLICT DO NOTHING;
