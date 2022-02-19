resource "cloudflare_zone" "marcuscraske-dot-co-uk" {
    zone = "marcuscraske.co.uk"
}

module "marcuscraske-dot-com-redirect" {
    source = "../dns-redirect"
    cloudflare_zone_id = cloudflare_zone.marcuscraske-dot-co-uk.id
    cloudflare_hostname = cloudflare_zone.marcuscraske-dot-co-uk.zone
}
