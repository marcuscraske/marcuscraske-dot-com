resource "cloudflare_zone" "marcuscraske-dot-com" {
    zone = "marcuscraske.com"
}

########################################################################################################################
# Main website
########################################################################################################################

resource "cloudflare_record" "marcuscraske-dot-com-1" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "185.199.108.153"
    type        = "A"
    proxied     = true
    ttl         = 1
}
resource "cloudflare_record" "marcuscraske-dot-com-2" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "185.199.109.153"
    type        = "A"
    proxied     = true
    ttl         = 1
}
resource "cloudflare_record" "marcuscraske-dot-com-3" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "185.199.110.153"
    type        = "A"
    proxied     = true
    ttl         = 1
}
resource "cloudflare_record" "marcuscraske-dot-com-4" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "185.199.111.153"
    type        = "A"
    proxied     = true
    ttl         = 1
}

resource "cloudflare_record" "www-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "www"
    value       = "1.2.3.4"
    type        = "A"
    proxied     = true
    ttl         = 1
}

resource "cloudflare_page_rule" "www-redirect" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    target      = "*www.marcuscraske.com/*"
    priority    = 1
    status      = "active"

    actions {
        forwarding_url {
            url         = "https://marcuscraske.com/$2"
            status_code = 301
        }
    }
}

resource "cloudflare_page_rule" "https" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    target      = "http://marcuscraske.com/*"
    priority    = 2
    actions {
        always_use_https = true
    }
}


########################################################################################################################
# E-mail
########################################################################################################################

resource "cloudflare_record" "txt-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "google-site-verification=p7_591zKvA5fykDvqTX8jRD35_LDT9GnKSmi69JJh3I"
    type        = "TXT"
}

resource "cloudflare_record" "alt4-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "alt4.aspmx.l.google.com"
    type        = "MX"
    priority    = "10"
}

resource "cloudflare_record" "alt3-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "alt3.aspmx.l.google.com"
    type        = "MX"
    priority    = "10"
}

resource "cloudflare_record" "alt2-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "alt2.aspmx.l.google.com"
    type        = "MX"
    priority    = "5"
}

resource "cloudflare_record" "alt1-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "alt1.aspmx.l.google.com"
    type        = "MX"
    priority    = "5"
}

resource "cloudflare_record" "aspmx-marcuscraske-dot-com" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "marcuscraske.com"
    value       = "aspmx.l.google.com"
    type        = "MX"
    priority    = "1"
}

########################################################################################################################
# Github pages verification
########################################################################################################################

resource "cloudflare_record" "marcuscraske-dot-com-gh-verify" {
    zone_id     = cloudflare_zone.marcuscraske-dot-com.id
    name        = "_github-pages-challenge-marcuscraske"
    value       = "1c97d1f9666d4c10c2548fad29232c"
    type        = "TXT"
    priority    = "1"
}
