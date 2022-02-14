provider "aws" {
    region = "eu-west-1"
}

resource "aws_s3_bucket" "tfstate-marcuscraske-dot-com" {
    bucket = "tfstate-marcuscraske-dot-com"
}

resource "aws_dynamodb_table" "tfstate-marcuscraske-dot-com" {
    name = "tfstate-marcuscraske-dot-com"
    hash_key = "LockID"
    read_capacity = 20
    write_capacity = 20

    attribute {
        name = "LockID"
        type = "S"
    }
}
