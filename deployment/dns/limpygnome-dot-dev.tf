resource "cloudflare_zone" "limpygnome-dot-dev" {
    zone = "limpygnome.dev"
}

module "limpygnome-dot-dev-redirect" {
    source = "../dns-redirect"
    cloudflare_zone_id = cloudflare_zone.limpygnome-dot-dev.id
    cloudflare_hostname = cloudflare_zone.limpygnome-dot-dev.zone
}
