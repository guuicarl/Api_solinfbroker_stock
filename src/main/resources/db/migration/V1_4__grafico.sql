create table if not exists grafico(
    id bigint primary key generated always as identity,
    id_stock bigint references stocks(id),
    aberto numeric,
    fechado numeric,
    high numeric,
    low numeric,
    created_on timestamp not null default current_timestamp
);